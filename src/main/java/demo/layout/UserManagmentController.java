package demo;

import demo.layout.UserBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import demo.errors.BadRequestExeption;
import demo.logic.UserManagmentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
public class UserManagmentController {

    private UserManagmentService userManagementService;

    @Autowired
    public UserManagmentController(UserManagmentService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @RequestMapping(path = "/users",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary store(@Valid @RequestBody UserBoundary user, BindingResult result)
    {
		if (result.hasErrors()) {
	        throw new BadRequestExeption();
	    }

		return this.userManagementService.store(user);

    }

    @RequestMapping(path = "/users/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary get(@PathVariable("email") String email)
    {
        return this.userManagementService.get(email);
    }

    @RequestMapping(path = "/users/login/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary login(@PathVariable("email") String email,
                                     @RequestParam(name="password", required = true)  String password)
    {
        return this.userManagementService.login(email, password);
    }

    @RequestMapping(path = "/users/{email}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@PathVariable("email") String email,
                           @RequestBody UserBoundary user)
    {
        this.userManagementService.updateUser(email, user);
    }

    @RequestMapping(path = "/users",
                    method = RequestMethod.DELETE)
    public void deleteAll()
    {
        this.userManagementService.deleteAll();
    }

    @RequestMapping(path = "/users/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary[] search(
            @RequestParam(name="criteriaType", required = false, defaultValue = "")  String criteriaType,
            @RequestParam(name="criteriaValue", required = false, defaultValue = "")  String value,
            @RequestParam(name="size", required = false, defaultValue = "10") String size,
            @RequestParam(name="page", required = false, defaultValue = "0") String page,
            @RequestParam(name="sortBy", required = false, defaultValue = "email") String sortBy,
            @RequestParam(name="sortOrder", required = false, defaultValue = "ASC") String sortOrder)
    {
        return this.userManagementService.search(criteriaType, value, size, page, sortBy, sortOrder);
    }
}
