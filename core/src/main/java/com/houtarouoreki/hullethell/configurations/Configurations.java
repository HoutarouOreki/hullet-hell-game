package com.houtarouoreki.hullethell.configurations;

import java.util.Dictionary;
import java.util.Hashtable;

public class Configurations {
    public final Dictionary<String, BodyConfiguration> bullets = new Hashtable<String, BodyConfiguration>();
    public final Dictionary<String, BodyConfiguration> environmentals = new Hashtable<String, BodyConfiguration>();
    public final Dictionary<String, BodyConfiguration> ships = new Hashtable<String, BodyConfiguration>();
}
