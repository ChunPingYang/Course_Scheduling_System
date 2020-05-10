package controller;

import edu.utdallas.csmc.web.dto.AdminUserFormResultDTO;
import edu.utdallas.csmc.web.dto.StudentDTO;
import edu.utdallas.csmc.web.dto.UserDataTableResultDTO;
import edu.utdallas.csmc.web.dto.UserResultDTO;
import edu.utdallas.csmc.web.service.AdminUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * This Class will have the path for all the APIs for the Admin.
 */
@Controller
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping("/home")
    public String adminHomePage(){
        return "role/admin/home.html";
    }

    @RequestMapping(value = {"/users/user/{netId}","/users/user"})
    public String getAllUsers(ModelMap model, @PathVariable(value = "netId", required = false) String netId){
        return "role/admin/user/list.html";
    }

    @RequestMapping("/users/user/create/{netId}")
    public String createUser(ModelMap model, @PathVariable("netId") String netId) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setNetId(netId);
        AdminUserFormResultDTO form = adminUserService.getNewUserForm();
        model.addAttribute("form", form);
        return "role/admin/user/add.html";
    }

    @RequestMapping("/users/user/create/submit/{netId}")
    public String submitNewUser(ModelMap model, @PathVariable("netId") String netId,
                                        @RequestParam String username,
                                        @RequestParam String firstname,
                                        @RequestParam String lastname,
                                        @RequestParam String cardid,
                                        @RequestParam String scancode,
                                        @RequestParam("role") String[] roleSelected) {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setNetId(netId);
        AdminUserFormResultDTO adminUserFormResultDTO = adminUserService.createNewUser(username,firstname,lastname,cardid,scancode,roleSelected);
        String message = adminUserFormResultDTO.getMessage();
        if(message!=null && !message.isEmpty()) {
            model.addAttribute("message", adminUserFormResultDTO.getMessage());
            model.addAttribute("form",adminUserFormResultDTO);
            return "role/admin/user/add.html";
        }
        return "redirect:/admin/users/user";
    }

    @RequestMapping("/users/user/update/{netId}/{userId}")
    public String updateUser(ModelMap model, @PathVariable("netId") String netId,  @PathVariable("userId") String userId){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setNetId(netId);

        AdminUserFormResultDTO form = adminUserService.getUpdateUserForm(UUID.fromString(userId));
        model.addAttribute("form", form);
        return "role/admin/user/edit.html";
    }

    @RequestMapping("/users/user/update/submit/{netId}/{userId}")
    public String submitUpdatedUser(ModelMap model,@PathVariable("netId") String netId,
                                @PathVariable("userId") String userId,
                                @RequestParam String username,
                                @RequestParam String firstname,
                                @RequestParam String lastname,
                                @RequestParam String cardid,
                                @RequestParam String scancode,
                                @RequestParam("role") String[] roleSelected) {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setNetId(netId);
        AdminUserFormResultDTO adminUserFormResultDTO = adminUserService.updateCreatedUser(UUID.fromString(userId),username,firstname,lastname,cardid,scancode,roleSelected);
        String message = adminUserFormResultDTO.getMessage();
        if(message!=null && !message.isEmpty()) {
            model.addAttribute("message", adminUserFormResultDTO.getMessage());
            model.addAttribute("form",adminUserFormResultDTO);
            return "role/admin/user/edit.html";
        }
        return "redirect:/admin/users/user";
    }


    @RequestMapping(value = "/users/user/queryByPage", method = RequestMethod.POST)
    public ResponseEntity<UserDataTableResultDTO> queryByPage(@Valid @RequestBody DataTablesInput input,
                                                              @RequestParam(value="role", required = false) String role) {
        UserDataTableResultDTO userDataTableResultDTO = adminUserService.queryUsersByPage(input);
        return ResponseEntity.ok(userDataTableResultDTO);
    }

    @RequestMapping("/users/user/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        System.out.println("userId: "+userId);
        adminUserService.deleteUser(UUID.fromString(userId));
        return "redirect:/admin/users/user";
    }
}
