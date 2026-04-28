package com.example.ecom.libraries;

import com.example.ecom.libraries.models.GLocation;

public interface MapsApi {
    int estimate(GLocation src, GLocation dest);
}
