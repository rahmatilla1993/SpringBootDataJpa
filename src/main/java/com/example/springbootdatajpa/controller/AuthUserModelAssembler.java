package com.example.springbootdatajpa.controller;

import com.example.springbootdatajpa.entity.AuthUser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthUserModelAssembler implements RepresentationModelAssembler<AuthUser, EntityModel<AuthUser>> {
    @Override
    public EntityModel<AuthUser> toModel(AuthUser authUser) {
        Link link = linkTo(methodOn(AuthUserController.class).getOne(authUser.getId().intValue())).withSelfRel();
        return EntityModel.of(authUser, link);
    }

    @Override
    public CollectionModel<EntityModel<AuthUser>> toCollectionModel(Iterable<? extends AuthUser> entities) {
        Link link = linkTo(methodOn(AuthUserController.class).findAll()).withRel("users");
        List<EntityModel<AuthUser>> entityModelList = new ArrayList<>();
        entities.forEach(authUser -> entityModelList.add(toModel(authUser)));
        return CollectionModel.of(entityModelList, link);
    }
}
