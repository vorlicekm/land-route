package com.pwc.test.landroute.service;

import com.pwc.test.landroute.model.Country;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoutingService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GraphService graphService;

    @Value("${coutriesUrl}")
    private String coutriesUrl;

    private List<Country> countries;

    public RoutingService(GraphService graphService) {
        this.graphService = graphService;
    }

    @PostConstruct
    public void init() {
        logger.info("loadCountries from {}", coutriesUrl);
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.TEXT_PLAIN));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        Country[] countries = restTemplate.getForObject(coutriesUrl, Country[].class);
        logger.info("loadCountries all countries, count={}", countries.length);
        this.countries =  Arrays.asList(countries);
    }

    public List<String> getRoute(String origin, String destination) {
        logger.info("getRoute: origin={}, destination={}", origin, destination);
        Country originCountry = countries.stream()
                .filter(country -> country.getCountryCode().equals(origin))
                .findFirst()
                .orElse(null);
        logger.info("getRoute: originCountry={}", originCountry);

        Country destinationCountry = countries.stream()
                .filter(country -> country.getCountryCode().equals(destination))
                .findFirst()
                .orElse(null);
        logger.info("getRoute: destinationCountry={}", destinationCountry);

        Map<String, List<String>> graph = new HashMap<>();
        for (Country country : countries) {
            graph.put(country.getCountryCode(), country.getCountryBorders());
        }

        return graphService.findShortestPathWithLimitedEdges(graph, originCountry.getCountryCode(), destinationCountry.getCountryCode(), 10);
    }
}
