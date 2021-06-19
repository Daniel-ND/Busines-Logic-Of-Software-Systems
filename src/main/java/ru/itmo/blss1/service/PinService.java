package ru.itmo.blss1.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.stereotype.Service;
import ru.itmo.blss1.data.dto.PinDTO;
import ru.itmo.blss1.data.entity.Pin;
import ru.itmo.blss1.data.entity.User;
import ru.itmo.blss1.data.repository.PinRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@AllArgsConstructor
public class PinService {
    UserService userService;
    PinRepository pinRepository;

    private boolean isCorrectURL(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new EntityExistsException("wrong URL");
        }
        if (uri.getHost().equals("i.pinimg.com"))
            return true;
        return false;
    }

    public Pin newPin(PinDTO pinDTO) {
        Pin pin = new Pin();
        pin.setUrl(pinDTO.url);
        pin.setUploadedBy(userService.getById(pinDTO.uploadedById));
        if (!isCorrectURL(pinDTO.url))
            throw new EntityExistsException("wrong URL");
        return pinRepository.save(pin);
    }

    public Pin getById(int id) {
        return pinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public Iterable<Pin> getAll() {
        return pinRepository.findAll();
    }
}
