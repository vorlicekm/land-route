package com.pwc.test.landroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GraphNode {
    String name;
    List<String> path;
    int edgeCount;
}
