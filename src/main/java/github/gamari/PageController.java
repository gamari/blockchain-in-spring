package github.gamari;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ページ表示用のコントローラー。
 */
@Controller
@RequestMapping("/page")
public class PageController {
	@GetMapping("/wallet")
	public String wallet(Model model) {
		model.addAttribute("message", "Hello Thymeleaf!!");
		return "wallet";
	}
}
