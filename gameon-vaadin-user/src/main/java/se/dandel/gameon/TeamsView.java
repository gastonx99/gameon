package se.dandel.gameon;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.Route;

import se.dandel.gameon.domain.model.Team;
import se.dandel.gameon.infrastructure.dto.TeamDto;
import se.dandel.gameon.infrastructure.dto.TeamDtoConverter;

@Route("teams")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class TeamsView extends VerticalLayout {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private TeamDtoConverter teamDtoConverter;

    @Inject
    private TeamEditForm teamEditForm;

    @PostConstruct
    public void init() {

        List<TeamDto> teamDtos = findTeams();
        Grid<TeamDto> teamGrid = new Grid<>(TeamDto.class);
        teamGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        teamGrid.addSelectionListener(event -> selectionChanged(event));
        teamGrid.setItems(teamDtos);

        add(teamGrid);

        addClassName("centered-content");
        add(teamEditForm);
    }

    private void selectionChanged(SelectionEvent<Grid<TeamDto>, TeamDto> event) {
        Optional<TeamDto> selectedItem = event.getFirstSelectedItem();
        LOGGER.info("Selection changed {}", selectedItem);
        teamEditForm.select(selectedItem);
    }

    private List<TeamDto> findTeams() {
        List<Team> teams = Arrays.asList(Team.of("asdf", "asdf"), Team.of("qwer", "qwer"));
        return teams.stream().map(t -> teamDtoConverter.convert(t)).collect(toList());
    }

}
