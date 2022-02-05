package com.cyecize.demo.web;

import com.cyecize.demo.api.database.DatabaseConnectDto;
import com.cyecize.demo.api.database.DatabaseProvider;
import com.cyecize.demo.api.database.DatabaseProviderDto;
import com.cyecize.demo.api.database.DatabaseService;
import com.cyecize.demo.constants.Endpoints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
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

    @PostMapping(Endpoints.DATABASE_CONNECT)
    @ResponseStatus(HttpStatus.OK)
    public void connectToDatabase(@Valid @RequestBody DatabaseConnectDto databaseConnectDto) {
        this.databaseService.connectToDatabase(databaseConnectDto);
    }

    @GetMapping(Endpoints.DATABASES)
    public List<String> listDatabases() {
        return this.databaseService.findAllDatabases();
    }
}
