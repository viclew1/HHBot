package fr.lewon.web.bot.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BodyMember {

	String value() default "";

}
