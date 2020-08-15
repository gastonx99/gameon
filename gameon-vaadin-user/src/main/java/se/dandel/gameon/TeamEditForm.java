package se.dandel.gameon;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import se.dandel.gameon.infrastructure.dto.TeamDto;
import se.dandel.gameon.infrastructure.dto.TeamDtoConverter;

public class TeamEditForm extends HorizontalLayout {
    @Inject
    private TeamDtoConverter teamDtoConverter;

    private TextField tfKey = new TextField("Key");
    private TextField tfName = new TextField("Name");

    private TeamDto currentTeam;
    private TeamDto originalTeam;

    private Binder<TeamDto> binder = new Binder<>(TeamDto.class);

    @PostConstruct
    public void init() {
        tfKey.addThemeName("bordered");
        tfName.addThemeName("bordered");

        Button bSave = new Button("Save", e -> saveTeam());
        Button bClear = new Button("Clear", e -> clearTeam());
        bSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        binder.bind(tfKey, TeamDto::getKey, TeamDto::setKey);
        binder.bind(tfName, TeamDto::getName, TeamDto::setName);

        add(tfKey, tfName, bClear, bSave);
        clearTeam();
    }

    private void clearTeam() {
        currentTeam = new TeamDto();
        binder.readBean(currentTeam);
    }

    private void saveTeam() {
        TeamDto teamDto = new TeamDto();
        teamDto.setKey(tfKey.getValue());
        teamDto.setName(tfKey.getValue());
    }

    public void select(Optional<TeamDto> selectedItem) {
        if (selectedItem.isPresent()) {
            originalTeam = selectedItem.get();
            currentTeam = teamDtoConverter.clone(selectedItem.get());
            binder.readBean(currentTeam);
        } else {
            clearTeam();
        }
    }
}
