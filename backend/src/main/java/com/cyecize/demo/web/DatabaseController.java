package com.cyecize.demo.web;

import com.cyecize.demo.api.database.DatabaseProvider;
import com.cyecize.demo.api.database.DatabaseProviderDto;
import com.cyecize.demo.api.database.DatabaseService;
import com.cyecize.demo.constants.Endpoints;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DatabaseController {

    private final ModelMapper modelMapper;

    private final DatabaseService databaseService;

    @GetMapping(Endpoints.DATABASE_PROVIDERS)
    public List<DatabaseProviderDto> getDatabaseProviders() {
        return Arrays.stream(DatabaseProvider.values())
                .map(provider -> this.modelMapper.map(provider, DatabaseProviderDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping(Endpoints.DATABASE_HAS_ESTABLISHED_CONNECTION)
    public boolean hasEstablishedConnection() {
        return this.databaseService.hasEstablishedConnection();
    }
}
