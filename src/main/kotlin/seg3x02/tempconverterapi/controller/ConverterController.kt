package seg3x02.tempconverterapi.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ConverterController {
    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("error", "")
        model.addAttribute("celsius", "")
        model.addAttribute("fahrenheit", "")
    }

    @RequestMapping("/")
    fun home(): String {
        return "home"
    }

    @GetMapping(value = ["/convert"])
    fun doConvert(
        @RequestParam(value = "celsius", required = false) celsius_input: String,
        @RequestParam(value = "fahrenheit", required = false) fahrenheit_input: String,
        @RequestParam(value = "operation", required = false) operation: String,
        model: Model
    ): String {
        val celsiusResult: Double
        val fahrenheitResult: Double
        when (operation) {
            "CelsiusToFarenheit" ->
                try {
                    celsiusResult = celsius_input.toDouble()
                    fahrenheitResult = ((celsiusResult * 9) / 5 + 32)
                    model.addAttribute("celsius", celsius_input)
                    model.addAttribute("fahrenheit", String.format("%.2f", fahrenheitResult))
                } catch (exp: NumberFormatException) {
                    model.addAttribute("error", "CelsiusFormatError")
                }
            "FarenheitToCelsius" ->
                try {
                    fahrenheitResult = fahrenheit_input.toDouble()
                    celsiusResult = ((fahrenheitResult - 32) * 5) / 9
                    model.addAttribute("celsius", String.format("%.2f", celsiusResult))
                    model.addAttribute("fahrenheit", fahrenheit_input)
                } catch (exp: NumberFormatException) {
                    model.addAttribute("error", "FahrenheitFormatError")
                }
            else -> {
                model.addAttribute("error", "OperationFormatError")
            }
        }
        return "home"
    }
}
