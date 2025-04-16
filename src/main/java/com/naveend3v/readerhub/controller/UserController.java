package com.naveend3v.readerhub.controller;

import com.naveend3v.readerhub.dto.cart.AddToCartDto;
import com.naveend3v.readerhub.dto.cart.CartDto;
import com.naveend3v.readerhub.dto.checkout.CheckoutItemDto;
import com.naveend3v.readerhub.dto.checkout.StripeResponse;
import com.naveend3v.readerhub.dto.order.OrderDto;
import com.naveend3v.readerhub.dto.request.JwtAuthRequest;
import com.naveend3v.readerhub.dto.response.AuthResponse;
import com.naveend3v.readerhub.dto.response.BookResponse;
import com.naveend3v.readerhub.dto.response.ErrorResponse;
import com.naveend3v.readerhub.dto.response.SuccessResponse;
import com.naveend3v.readerhub.exceptions.CustomException;
import com.naveend3v.readerhub.exceptions.ProductNotExistException;
import com.naveend3v.readerhub.jwt.JwtService;
import com.naveend3v.readerhub.model.Bookslist;
import com.naveend3v.readerhub.model.UserInfo;
import com.naveend3v.readerhub.service.BookslistService;
import com.naveend3v.readerhub.service.CartService;
import com.naveend3v.readerhub.service.OrderService;
import com.naveend3v.readerhub.service.UserInfoService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BookslistService bookslistService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public UserInfo getAuthenticatedtUser() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException("User is not authenticated");
        }
        UserInfo userInfo = userInfoService.findByName(authentication.getName()).orElse(null);
        System.out.println("Auth user: " + authentication.getName());
        if (userInfo == null) {
            throw new CustomException("User not found");
        }
        return userInfo;
    }

    @PostMapping("/signup")
    public ResponseEntity addUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_USER");
        String resp = userInfoService.saveUser(userInfo);
        if(!resp.equals("User updated successfully")){
            return new ResponseEntity("Error adding the user " + userInfo.getName(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity("User " + userInfo.getName()+ " registered successfully.",HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity authenticateAndGetToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        if (jwtAuthRequest.getUsername() == null || jwtAuthRequest.getUsername().isEmpty() ||
                jwtAuthRequest.getPassword() == null || jwtAuthRequest.getPassword().isEmpty()) {
            return ErrorResponse.generateResp("Username or password cannot be empty!", HttpStatus.BAD_REQUEST);
        }

        Optional<UserInfo> user = userInfoService.findByName(jwtAuthRequest.getUsername());

        if (user.isEmpty()) {
            return ErrorResponse.generateResp("Invalid username or password!", HttpStatus.NOT_FOUND);
        }

        if (user.isPresent() && user.get().getRoles().contains("ROLE_USER")) {
            try {
                Authentication auth = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword())
                );
                if (auth.isAuthenticated()) {
                    return AuthResponse.generateResp(jwtService.generateToken(jwtAuthRequest.getUsername()), HttpStatus.OK);
                }
            } catch (Exception e) {
                return ErrorResponse.generateResp("Invalid username or password!", HttpStatus.UNAUTHORIZED);
            }
        }
        return ErrorResponse.generateResp("Invalid username or password!", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/books")
    public ResponseEntity getAllBooks() {
        return SuccessResponse.generateResp(bookslistService.findAllBooks(),HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBooksByID(@PathVariable Integer id){
        Integer booksSize = bookslistService.findAllBooks().size();
        Optional<Bookslist> book = Optional.empty();

        System.out.println("Book size : " + booksSize);
        try {
            return BookResponse.generateResp(bookslistService.findByBook(id),HttpStatus.OK);
        } catch (Exception error){
            return ErrorResponse.generateResp(error.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDto addToCartDto){
        cartService.addToCart(addToCartDto,getAuthenticatedtUser());
        return SuccessResponse.generateResp("Added to Cart",HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCartItems(){
        CartDto cartDto = cartService.listCartItems(getAuthenticatedtUser());
        return SuccessResponse.generateResp(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/cart/delete/{cartItemID}")
    public ResponseEntity<?> DeleteCartItem(@PathVariable Integer cartItemID){
        cartService.deleteCart(cartItemID,getAuthenticatedtUser());
        return SuccessResponse.generateResp("Cart deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/cart/update/{cartItemID}")
    public ResponseEntity<?> updateCartItem(@PathVariable Integer cartItemID, @RequestBody AddToCartDto cartDto){
        cartService.updateCartItem(getAuthenticatedtUser(),cartItemID,cartDto);
        return SuccessResponse.generateResp("Cart updated successfully!",HttpStatus.OK);
    }

    @PostMapping("/order/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDto) throws StripeException {
        Session session = orderService.createSession(checkoutItemDto);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return ResponseEntity.ok(stripeResponse);
    }

    @PostMapping("/order/add")
    public ResponseEntity<?> placeOrder(@RequestBody StripeResponse stripeResponse) throws ProductNotExistException, CustomException {
        UserInfo userInfo = getAuthenticatedtUser();
        orderService.placeOrder(userInfo,stripeResponse.getSessionId());
        return SuccessResponse.generateResp("Orders has been placed!",HttpStatus.CREATED);
    }

    @GetMapping("/order/allOrders")
    public ResponseEntity<?> getAllOrdersForUser(){
        UserInfo userInfo = getAuthenticatedtUser();
        List<OrderDto> ordersDtoList = orderService.getUserOrders(userInfo.getId());
        return SuccessResponse.generateResp(ordersDtoList,HttpStatus.OK);
    }


}
