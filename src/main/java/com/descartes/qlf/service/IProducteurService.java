package com.descartes.qlf.service;

import com.descartes.qlf.dto.ProducteurDTO;
import com.descartes.qlf.model.Producteur;

public interface IProducteurService {

    Producteur register(ProducteurDTO producteurDTO);

}
