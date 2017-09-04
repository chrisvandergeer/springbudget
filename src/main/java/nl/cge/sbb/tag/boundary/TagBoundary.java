package nl.cge.sbb.tag.boundary;

import nl.cge.sbb.arch.boundary.BoundaryResult;
import nl.cge.sbb.tag.control.DeleteAllTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chris on 16-05-17.
 */
@RestController
@RequestMapping("/tag")
public class TagBoundary {

    @Autowired
    private DeleteAllTagsService deleteAllTagsService;

    @RequestMapping(method = RequestMethod.DELETE)
    public BoundaryResult<Void> deleteAll() {
        int totaal = deleteAllTagsService.delete();

        return BoundaryResult.success()
                .setMessage(String.format("Aantal verwijderde tags: %s", totaal));
    }

}
