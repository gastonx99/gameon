package se.dandel.gameon.adapter.out.rest.api1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.dandel.gameon.domain.model.RemoteKey;
import se.dandel.gameon.domain.model.Tournament;
import se.dandel.gameon.domain.model.TournamentType;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Api1PortAdapterTest {

    private static final String COUNTRY_CODE = "se";
    public static final RemoteKey REMOTE_KEY_COUNTRY = RemoteKey.of("114");
    public static final RemoteKey REMOTE_KEY_TOURNAMENT = RemoteKey.of(17);

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private Api1PortInvoker invoker;

    @InjectMocks
    private Api1PortAdapter adapter;

    @Captor
    private ArgumentCaptor<Map<String, String>> mapArgumentCaptor;

    @Test
    void fetchCountry() {
        // Given
        CountryDTO expected = new CountryDTO();
        when(invoker.invoke(any(), any(), any())).thenReturn(Collections.singletonList(expected));

        // When
        adapter.fetchCountry();

        // Then
        verify(invoker).invoke(any(), any(), mapArgumentCaptor.capture());
        assertTrue(mapArgumentCaptor.getValue().isEmpty());
        verify(dtoMapper).fromDTO(expected);
    }


    @Test
    void fetchTeams() {
        // Given
        TeamDTO expected = new TeamDTO();
        when(invoker.invoke(any(), any(), any())).thenReturn(Collections.singletonList(expected));

        // When
        adapter.fetchTeams(REMOTE_KEY_COUNTRY);

        // Then
        verify(invoker).invoke(any(), any(), mapArgumentCaptor.capture());
        assertTrue(mapArgumentCaptor.getValue().containsKey("country_id"));
        assertTrue(mapArgumentCaptor.getValue().containsValue(REMOTE_KEY_COUNTRY.getRemoteKey()));
        verify(dtoMapper).fromDTO(expected);
    }

    @Test
    void fetchAllLeagues() {
        // Given
        LeagueDTO expected = new LeagueDTO();
        when(invoker.invoke(any(), any(), any())).thenReturn(Collections.singletonList(expected));

        // When
        adapter.fetchLeagues(Optional.empty());

        // Then
        verify(invoker).invoke(any(), any(), mapArgumentCaptor.capture());
        assertTrue(mapArgumentCaptor.getValue().isEmpty());
        verify(dtoMapper).fromDTO(expected);
    }

    @Test
    void fetchLeaguesForCountry() {
        // Given
        LeagueDTO expected = new LeagueDTO();
        when(invoker.invoke(any(), any(), any())).thenReturn(Collections.singletonList(expected));

        // When
        adapter.fetchLeagues(Optional.of(REMOTE_KEY_COUNTRY));

        // Then
        verify(invoker).invoke(any(), any(), mapArgumentCaptor.capture());
        assertTrue(mapArgumentCaptor.getValue().containsKey("country_id"));
        assertTrue(mapArgumentCaptor.getValue().containsValue(REMOTE_KEY_COUNTRY.getRemoteKey()));
        verify(dtoMapper).fromDTO(expected);
    }

    @Test
    void fetchSeasons() {
        // Given
        SeasonDTO expected = new SeasonDTO();
        when(invoker.invoke(any(), any(), any())).thenReturn(Collections.singletonList(expected));

        // When
        adapter.fetchSeasons(REMOTE_KEY_TOURNAMENT);

        // Then
        verify(invoker).invoke(any(), any(), mapArgumentCaptor.capture());
        assertTrue(mapArgumentCaptor.getValue().containsKey("league_id"));
        assertTrue(mapArgumentCaptor.getValue().containsValue(REMOTE_KEY_TOURNAMENT.getRemoteKey()));
        verify(dtoMapper).fromDTO(expected);
    }

}