package com.cyecize.demo.web;

import com.cyecize.demo.api.database.CreateDatabaseDto;
import com.cyecize.demo.api.database.DatabaseConnectDto;
import com.cyecize.demo.api.database.DatabaseProvider;
import com.cyecize.demo.api.database.DatabaseProviderDto;
import com.cyecize.demo.api.database.DatabaseService;
import com.cyecize.demo.api.user.CreateUserDto;
import com.cyecize.demo.api.user.UserService;
import com.cyecize.demo.constants.Endpoints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("isAnonymous()")
public class DatabaseController {

    private final ModelMapper modelMapper;

    private final DatabaseService databaseService;

    private final UserService userService;

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
        this.databaseService.connectToSQLServer(databaseConnectDto);
    }

    @PostMapping(Endpoints.DATABASE_SELECT)
    @ResponseStatus(HttpStatus.OK)
    public void selectDatabase(@Valid @RequestBody SelectDatabaseDto selectDatabaseDto) {
        this.databaseService.selectDatabase(selectDatabaseDto.getSelectedDatabase());
    }

    @GetMapping(Endpoints.DATABASE_SELECTED)
    public CurrentDatabaseDto getSelectedDatabase() {
        return new CurrentDatabaseDto(this.databaseService.getSelectedDatabase());
    }

    @PostMapping(Endpoints.DATABASE_CREATE)
    @ResponseStatus(HttpStatus.OK)
    public void createDatabase(@Valid @RequestBody CreateDatabaseDto dto) {
        this.databaseService.createDatabase(dto.getDatabaseName());
        this.userService.createAdmin(new CreateUserDto(dto.getAdminUsername(), dto.getAdminPassword()));
    }

    @GetMapping(Endpoints.DATABASES)
    public List<String> listDatabases() {
        return this.databaseService.findAllDatabases();
    }

    @Data
    static class SelectDatabaseDto {
        @NotEmpty
        private String selectedDatabase;
    }

    @Data
    @AllArgsConstructor
    static class CurrentDatabaseDto {
        private String database;
    }
}
