package nl.cge.sbb.transaktie;

import nl.cge.sbb.arch.boundary.BoundaryResult;
import nl.cge.sbb.transaktie.boundary.TransaktieBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

/**
 * Created by chris on 16-05-17.
 */
@Service
public class InitAfterStartupService {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private TransaktieBoundary transaktieBoundary;

    @PostConstruct
    public void init() {
        logger.info("Start importeren transakties");
        BoundaryResult<Void> result = transaktieBoundary.importeer();
        logger.info(result.getMessage());
    }
}
