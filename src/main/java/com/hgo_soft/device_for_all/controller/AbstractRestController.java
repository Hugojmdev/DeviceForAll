package com.hgo_soft.device_for_all.controller;

import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRestController {
    protected <T> ResponseEntity<T> okOrNotFound(T body) {
        return body == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(body);
    }

    protected <T> ResponseEntity<T> okOrNotFound(Optional<T> optional) {
        return optional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    protected <T> ResponseEntity<List<T>> okOrEmpty(List<T> list) {
        return ResponseEntity.ok(Optional.ofNullable(list).orElse(Collections.emptyList()));
    }

    protected <T> ResponseEntity<T> created(String locationUri, T body) {
        return ResponseEntity.created(URI.create(locationUri)).body(body);
    }

    protected ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<Void> deletedSuccessfully() {
        return ResponseEntity.ok().build(); // or noContent()
    }
}