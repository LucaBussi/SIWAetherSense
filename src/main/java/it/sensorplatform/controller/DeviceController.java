package it.sensorplatform.controller;

import it.sensorplatform.dto.DeviceDTO;
import it.sensorplatform.model.Credentials;
import it.sensorplatform.model.Device;
import it.sensorplatform.model.Project;
import it.sensorplatform.service.CredentialsService;
import it.sensorplatform.service.DeviceService;
import it.sensorplatform.service.ProjectService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private CredentialsService credentialsService;

   
    @GetMapping("/admin/manageProjectDevices/{projectId}")
    public String manageProjectDevices(@PathVariable("projectId") Long projectId,
            @RequestParam(value = "deviceQuery", required = false) String deviceQuery,Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
	    model.addAttribute("user", credentials);
        Project project = projectService.getProjectById(projectId);
        Set<Device> devicesFiltred;
        if (deviceQuery != null && !deviceQuery.isEmpty()) {
            devicesFiltred = deviceService.findByNameStartingWithIgnoreCase(deviceQuery);
            devicesFiltred.addAll(deviceService.findByMacAddressStartingWithIgnoreCase(deviceQuery));
            devicesFiltred.addAll(deviceService.findByEmailOwnerStartingWithIgnoreCase(deviceQuery));
            devicesFiltred.addAll(deviceService.findByTod_NameStartingWithIgnoreCase(deviceQuery));
        } else {
            devicesFiltred = deviceService.findAllByProjectId(projectId);
        }
        List<Device> devices = new ArrayList<>(devicesFiltred);
        this.loadDeviceDTO(devices, model);
        model.addAttribute("project", project);
        return "admin/manageProjectDevices.html";
    }
   
    @GetMapping("/admin/formNewDevice/{projectId}")
    public String formNewDevice(@PathVariable("projectId") Long projectId, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
	    model.addAttribute("user", credentials);
        Project project = projectService.getProjectById(projectId);
        Device device = new Device();
        device.setProject(project);
        model.addAttribute("device", device);
        model.addAttribute("project", project);
        model.addAttribute("tods", project.getTods());
        return "admin/formNewDevice.html";
    }

   
    @PostMapping("/admin/newDevice/{projectId}")
    public String saveDevice(@Valid @ModelAttribute("device") Device device,
                             BindingResult bindingResult,
                             @PathVariable("projectId") Long projectId,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        Project project = projectService.getProjectById(projectId);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
	    model.addAttribute("user", credentials);

        if (bindingResult.hasErrors()) {
        	device.setProject(project);
            model.addAttribute("device", device);
            model.addAttribute("project", project);
            model.addAttribute("tods", project.getTods());
            return "admin/formNewDevice.html";
        }
        
        if(device.getTod()==null) {
        	device.setProject(project);
            model.addAttribute("device", device);
            model.addAttribute("project", project);
            model.addAttribute("tods", project.getTods());
            model.addAttribute("noTodSelected", "Please choose a type of device.");
            return "admin/formNewDevice.html";
        }
        
        if(deviceService.existsByMacAddress(device.getMacAddress())) {
            model.addAttribute("device", device);
            model.addAttribute("project", project);
            model.addAttribute("tods", project.getTods());
            model.addAttribute("duplicateDeviceError", "A device with this MAC Address already exists");
            return "admin/formNewDevice.html";
        }
        device.setProject(project);
        deviceService.saveDevice(device);
        redirectAttributes.addFlashAttribute("successMessage", "Device added successfully!");
        List<Device> devices = new ArrayList<>(deviceService.findAllByProjectId(projectId));
        this.loadDeviceDTO(devices, model);
        model.addAttribute("project", project);
        return "redirect:/admin/manageProjectDevices/" + projectId;
    }

    
    @GetMapping("/admin/formUpdateDevice/{projectId}/{macAddress}")
    public String formUpdateDevice(@PathVariable("projectId") Long projectId,
                                   @PathVariable("macAddress") String macAddress,
                                   Model model) {
    	Project project = projectService.getProjectById(projectId);
    	Device device = deviceService.findByMacAddress(macAddress);
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("user", credentials);
    	model.addAttribute("project", project);
    	model.addAttribute("device", device);
        return "admin/formUpdateDevice.html";
    }
    
    @PostMapping("/admin/updateDevice/{projectId}/{macAddress}")
    public String adminUpdateDevice(@PathVariable("projectId") Long projectId,
                               @PathVariable("macAddress") String macAddress,
                               @RequestParam String name,
                               @RequestParam Double latitude,
                               @RequestParam Double longitude,
                               RedirectAttributes redirectAttributes) {
    	
    	if (name == null || name.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Device name is required.");
            return "redirect:/admin/manageProjectDevices/" + projectId;
        }
    	
        // Recupera il dispositivo tramite MAC address
        Device device = deviceService.findByMacAddress(macAddress);
        
        if (device == null) {
            redirectAttributes.addFlashAttribute("error", "Device not found.");
            return "redirect:/admin/manageProjectDevices/" + projectId;
        }

        // Aggiorna solo i campi modificabili
        device.setName(name);
        device.setLatitude(latitude);
        device.setLongitude(longitude);

        // Salvataggio
        deviceService.save(device);

        redirectAttributes.addFlashAttribute("success", "Device updated successfully.");
        return "redirect:/admin/manageProjectDevices/" + projectId;
    }
 
    @PostMapping("/admin/deleteDevice/{projectId}/{macAddress}")
    public String deleteDevice(@PathVariable("projectId") Long projectId,
    		  				   @PathVariable("macAddress") String macAddress,
                               RedirectAttributes redirectAttributes) {
    	Device device = deviceService.findByMacAddress(macAddress);
        deviceService.delete(device);
        redirectAttributes.addFlashAttribute("successMessage", "Dispositivo eliminato.");
        return "redirect:/admin/manageProjectDevices/" + projectId;
    }
    
    @GetMapping("/device/{projectId}/{macAddress}")
    public String aboutDevice(@PathVariable("projectId") Long projectId, 
    							@PathVariable("macAddress") String macAddress, Model model) {
    	Project project = projectService.getProjectById(projectId);
    	Device device = deviceService.findByMacAddress(macAddress);
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("user", credentials);
    	model.addAttribute("project", project);
    	model.addAttribute("device", device);
    	return "updateDevice";
    }
    
    @PostMapping("/updateDevice/{projectId}/{macAddress}")
    public String updateDevice(@PathVariable Long projectId,
                               @PathVariable String macAddress,
                               @RequestParam String name,
                               @RequestParam Double latitude,
                               @RequestParam Double longitude,
                               RedirectAttributes redirectAttributes) {
    	
    	if (name == null || name.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Device name is required.");
            return "redirect:/device/" + projectId + "/" + macAddress;
        }
    	
        // Recupera il dispositivo tramite MAC address
        Device device = deviceService.findByMacAddress(macAddress);
        
        if (device == null) {
            redirectAttributes.addFlashAttribute("error", "Device not found.");
            return "redirect:/device/" + projectId + "/" + macAddress;
        }

        // Aggiorna solo i campi modificabili
        device.setName(name);
        device.setLatitude(latitude);
        device.setLongitude(longitude);

        // Salvataggio
        deviceService.save(device);

        redirectAttributes.addFlashAttribute("success", "Device updated successfully.");
        return "redirect:/device/" + projectId + "/" + macAddress;
    }

    
	public void loadDeviceDTO(List <Device> devices, Model model) {
		List<DeviceDTO> deviceDTOs = devices.stream()
                .map(d -> new DeviceDTO(d.getName(), d.getMacAddress(), d.getEmailOwner(), d.getDevEui(), d.getLongitude(), d.getLatitude(), d.getTod().getName()))
                .collect(Collectors.toList());
		Collections.sort(deviceDTOs, new Comparator<DeviceDTO>() {

			@Override
			public int compare(DeviceDTO d1, DeviceDTO d2) {
				
				return d1.getEmailOwner().compareTo(d2.getEmailOwner());
			}
			
		});
        model.addAttribute("devices", deviceDTOs);
	}
}