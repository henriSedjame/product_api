package io.github.hsedjame.data.entities;

import java.io.Serializable;
import java.util.List;

public record Distributor(String name, List<String> cities) implements Serializable {}
