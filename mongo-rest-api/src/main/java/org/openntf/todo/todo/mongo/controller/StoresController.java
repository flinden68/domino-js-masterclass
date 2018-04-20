package org.openntf.todo.todo.mongo.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.openntf.todo.todo.mongo.Util;
import org.openntf.todo.todo.mongo.exceptions.DatabaseModuleException;
import org.openntf.todo.todo.mongo.exceptions.StoreNotFoundException;
import org.openntf.todo.todo.mongo.model.Store;
import org.openntf.todo.todo.mongo.model.User;
import org.openntf.todo.todo.mongo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/v1/stores")
public class StoresController {

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "Get All the stores of the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all the stores"),
            @ApiResponse(code = 500, message = "Internal error, go check the logs")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-TODO-API-KEY", value = "", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "X-TODO-USER-KEY", value = "", required = false, dataType = "string", paramType = "header")
    })
    @RequestMapping(method = RequestMethod.GET, value = "", produces = "application/json")
    public ResponseEntity<?> getStores() {
        try {
            List<Store> stores = storeService.getStoresForCurrentUser();
            return new ResponseEntity<>(stores, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Mark all the todo's who are overdue")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all the stores"),
            @ApiResponse(code = 500, message = "Internal error, go check the logs")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-TODO-API-KEY", value = "", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "X-TODO-USER-KEY", value = "", required = false, dataType = "string", paramType = "header")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/todos/markOverdue")
    public ResponseEntity<?> markOverdue(final @RequestHeader(value = "updateUrl") String nextUrl) {
        try {
            storeService.markOverdue();
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
