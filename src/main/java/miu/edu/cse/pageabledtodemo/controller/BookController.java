package miu.edu.cse.pageabledtodemo.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import miu.edu.cse.pageabledtodemo.dto.BookRequestDto;
import miu.edu.cse.pageabledtodemo.dto.BookResponseDto;
import miu.edu.cse.pageabledtodemo.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class BookController {

    private  BookService bookService;

    @GetMapping("/{pageNo}/{pageSize}/{direction}/{sortBy}")
    public ResponseEntity<List<BookResponseDto>> getAllBooks(
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize,
            @PathVariable String direction,
            @PathVariable String sortBy

    ) {
        Page<BookResponseDto> bookResponseDtos =
                bookService.findAll(pageNo, pageSize, direction, sortBy);
        if (bookResponseDtos.getTotalElements() > 0) {
            return ResponseEntity.ok(bookResponseDtos.getContent());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Optional<List<BookResponseDto>>> createBooks(
            @RequestBody List<BookRequestDto> bookRequestDtos
    ) throws URISyntaxException {
        Optional<List<BookResponseDto>> bookResponseDtos = bookService.addAllBooks(bookRequestDtos);
        if (bookResponseDtos.isPresent()) {
            return ResponseEntity.created(new URI("/books")).body(bookResponseDtos);
        }
        return ResponseEntity.noContent().build();
    }

}
