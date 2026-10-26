package com.hgo_soft.device_for_all.common.security;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@RestController
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("denyAll()")
public @interface SecuredRestController {
}
