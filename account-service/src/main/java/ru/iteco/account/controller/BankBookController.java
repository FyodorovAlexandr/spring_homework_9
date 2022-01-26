package ru.iteco.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.iteco.account.model.dto.BankBookDto;
import ru.iteco.account.service.BankBookService;
import ru.iteco.account.validation.Created;
import ru.iteco.account.validation.Update;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/bank-book")
@Validated
public class BankBookController {

    private final BankBookService bankBookService;

    @GetMapping({"/{id}", "/"})
    public ResponseEntity<BankBookDto> getBankBookById(@Min(2) @PathVariable Integer id) {
        return ResponseEntity.ok(bankBookService.findById(id));
    }

    @GetMapping("/by-user-id/")
    public ResponseEntity<List<BankBookDto>> getBankBookByUserId(@CookieValue Integer userId, @RequestHeader Map<String, String> headers) {
        log.info("Headers: {}", headers);
        return ResponseEntity.ok(bankBookService.findByUserId(userId));
    }

    @Validated(Created.class)
    @PostMapping
    public ResponseEntity<BankBookDto> createBankBook(@Valid @RequestBody BankBookDto bankBookDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bankBookService.create(bankBookDto));
    }

    @Validated(Update.class)
    @PutMapping
    public BankBookDto updateBankBook(@Valid @RequestBody BankBookDto bankBookDto) {
        return bankBookService.update(bankBookDto);
    }

    @DeleteMapping({"/{id}", "/"})
    public void deleteBankBook(@PathVariable Integer id) {
        bankBookService.delete(id);
    }

    @DeleteMapping({"/by-user-id/{id}", "/by-user-id/"})
    public void deleteBankBookByUserId(@PathVariable Integer userId) {
        bankBookService.deleteByUserId(userId);
    }

}
