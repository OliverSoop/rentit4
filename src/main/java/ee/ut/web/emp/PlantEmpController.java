package ee.ut.web.emp;
import ee.ut.model.Plant;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/emp/plant")
@Controller
@RooWebScaffold(path = "emp/plant", formBackingObject = Plant.class, update = false, delete = false, create = false)
public class PlantEmpController {
}
