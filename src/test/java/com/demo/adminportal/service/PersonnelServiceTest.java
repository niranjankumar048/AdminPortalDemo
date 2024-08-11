package com.demo.adminportal.service;

import com.demo.adminportal.model.Personnel;
import com.demo.adminportal.repository.PersonnelRepository;
import com.demo.adminportal.utility.PasswordUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonnelServiceTest {

    @Mock
    private PersonnelRepository personnelRepository;

    @InjectMocks
    private PersonnelService personnelService;

    private Personnel personnel1;
    private Personnel personnel2;

    @BeforeEach
    public void setUp() {
        personnel1 = new Personnel();
        personnel1.setId(1L);
        personnel1.setFirstName("user1");
        personnel1.setLastName("user1 lastName");
        personnel1.setUsername("user1");
        personnel1.setPassword("password");
        personnel1.setEmailAddress("user1@example.com");
        personnel1.setTelephoneNumber("1234567890");

        personnel2 = new Personnel();
        personnel2.setId(2L);
        personnel2.setFirstName("user2");
        personnel2.setLastName("user2 lastName");
        personnel2.setUsername("user2");
        personnel2.setPassword("password");
        personnel2.setEmailAddress("user2@example.com");
        personnel2.setTelephoneNumber("0987654321");
    }

    @Test
    public void testGetAllPersonnel() {
        when(personnelRepository.findAll()).thenReturn(Arrays.asList(personnel1, personnel2));

        List<Personnel> personnelList = personnelService.getAllPersonnel();

        assertNotNull(personnelList);
        assertEquals(2, personnelList.size());
        assertEquals("user1", personnelList.get(0).getFirstName());
        assertEquals("user2", personnelList.get(1).getFirstName());
    }

    @Test
    public void testGetPersonnelById() {
        when(personnelRepository.findById(1L)).thenReturn(Optional.of(personnel1));

        Personnel foundPersonnel = personnelService.getPersonnelById(1L);

        assertNotNull(foundPersonnel);
        assertEquals("user1", foundPersonnel.getFirstName());
    }

    @Test
    public void testCreatePersonnel() {
        when(personnelRepository.save(any(Personnel.class))).thenReturn(personnel1);

        Personnel savedPersonnel = personnelService.createPersonnel(personnel1);

        assertNotNull(savedPersonnel);
        assertEquals("user1", savedPersonnel.getFirstName());
        verify(personnelRepository, times(1)).save(personnel1);
    }

    @Test
    public void testUpdatePersonnel() {
        when(personnelRepository.findById(1L)).thenReturn(Optional.of(personnel1));
        when(personnelRepository.save(any(Personnel.class))).thenReturn(personnel1);

        personnel1.setFirstName("Updated Name");
        Personnel updatedPersonnel = personnelService.updatePersonnel(1L, personnel1);

        assertNotNull(updatedPersonnel);
        assertEquals("Updated Name", updatedPersonnel.getFirstName());
        verify(personnelRepository, times(1)).save(personnel1);
    }

    @Test
    public void testDeletePersonnel() {
        when(personnelRepository.findById(1L)).thenReturn(Optional.of(personnel1));

        personnelService.deletePersonnel(1L);

        verify(personnelRepository, times(1)).delete(personnel1);
    }
}

