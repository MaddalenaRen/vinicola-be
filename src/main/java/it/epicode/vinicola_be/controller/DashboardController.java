package it.epicode.vinicola_be.controller;

import it.epicode.vinicola_be.dto.DashboardDto;
import it.epicode.vinicola_be.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("")
    public DashboardDto getDashboard(){
        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setFaseLottiDto(dashboardService.getFaseLotti());

        return dashboardDto;
    }

}
