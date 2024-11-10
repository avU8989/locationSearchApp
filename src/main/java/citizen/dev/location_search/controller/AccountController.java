package citizen.dev.location_search.controller;

import citizen.dev.location_search.entities.Account;
import citizen.dev.location_search.security.JwtTokenUtil;
import citizen.dev.location_search.services.CustomUserDetailsService;
import citizen.dev.location_search.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * POST /accounts : Create a new account
     *
     * @param account (required)
     * @return Account created (status code 201)
     * or Error during creating Account (status code 400)
     */
    @Operation(
            operationId = "accountsPost",
            summary = "Create a new account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Error during creating Account")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/accounts",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<?> signUp(@Parameter(name = "Account", description = "", required = true) @Valid @RequestBody Account account) {
        logger.info("Creating an account");
        userService.saveAccount(account);

        // Build the URI for the newly created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.getId())
                .toUri();

        // Return 201 Created with the location of the new resource
        return ResponseEntity.created(location).body(account);

    }
}
