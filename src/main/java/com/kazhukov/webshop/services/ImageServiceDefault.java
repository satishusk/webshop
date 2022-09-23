package com.kazhukov.webshop.services;

import com.kazhukov.webshop.entities.Image;
import com.kazhukov.webshop.exceptions.ImageNotFoundException;
import com.kazhukov.webshop.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceDefault implements ImageService{
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceDefault(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image create(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> createImages(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
            .map(multipartFile -> create(new Image(multipartFile)))
            .collect(Collectors.toList());
    }

    public Image findById(long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
    }

    @Override
    public long count() {
        return imageRepository.count();
    }
}
