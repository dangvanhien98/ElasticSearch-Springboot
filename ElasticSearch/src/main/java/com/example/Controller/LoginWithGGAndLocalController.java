package com.example.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Model.CustomerModel;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class LoginWithGGAndLocalController {

	@GetMapping
	public String home(Model model, @AuthenticationPrincipal OAuth2User user) {
		if (user != null) {
			String name = user.getAttribute("name");
			String email = user.getAttribute("email");
			model.addAttribute("name", name);
			model.addAttribute("email", email);
			String[] names = name.split("\\s");
			for (String n : names) {
				System.out.println(n);
			}
			return "index";
		} else
			return "redirect:/login";
	}

	@GetMapping("/login")
	private String login(Model model) {
		model.addAttribute("customer", new CustomerModel());
		return "login";
	}

	@PostMapping("/check-login")
	public ResponseEntity<String> checkLoginWithlocal(@RequestBody @Valid CustomerModel cus) {
		System.out.println(cus);
		return new ResponseEntity<String>("Login Success with local", HttpStatus.OK);
	}

	@GetMapping("/logout")
	private String logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookie = request.getCookies();
		cookie[0].setMaxAge(0);
		cookie[0].setSecure(true);
		cookie[0].setHttpOnly(true);
		cookie[0].setPath("/");

		// add cookie to response
		response.addCookie(cookie[0]);
		return "login";
	}
}
