package com.pwc.test.landroute.controller;

import com.pwc.test.landroute.dto.RouteDto;
import com.pwc.test.landroute.service.RoutingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/routing")
public class RoutingController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RoutingService routingService;

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<RouteDto> getRouting(@PathVariable String origin, @PathVariable String destination) {
        logger.info("getRouting: origin={}, destination={}", origin, destination);
        List<String> path = routingService.getRoute(origin, destination);
        if (path == null) {
            logger.info("There is no land route between {} and {}", origin, destination);
            return ResponseEntity.badRequest().build();
        }
        RouteDto routeDto = new RouteDto();
        routeDto.setRoute(path);
        return ResponseEntity.ok(routeDto);
    }
}
