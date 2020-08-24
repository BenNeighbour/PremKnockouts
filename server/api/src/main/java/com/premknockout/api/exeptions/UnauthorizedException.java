package com.premknockout.api.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * @created 03/07/2020 - 20
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 3508469030597908848L;

    public UnauthorizedException(String reason) {
        super(reason);
    }

}
