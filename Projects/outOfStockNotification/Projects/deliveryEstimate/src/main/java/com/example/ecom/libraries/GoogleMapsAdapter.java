package com.example.ecom.libraries;

import org.springframework.stereotype.Component;

import com.example.ecom.libraries.models.GLocation;

@Component
public class GoogleMapsAdapter implements MapsApi {
    private GoogleMapsApi googleMapsApi;

    public GoogleMapsAdapter() {
        this.googleMapsApi = new GoogleMapsApi();
    }
    @Override
    public int estimate(GLocation src, GLocation dest) {
        return googleMapsApi.estimate(src, dest);
    }
}
