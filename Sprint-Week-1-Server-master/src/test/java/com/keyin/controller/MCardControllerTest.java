package com.keyin.controller;

import com.keyin.model.MCard;
import com.keyin.service.MCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MCardController.class)
public class MCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MCardService mCardService;

    private MCard mCard;

    @BeforeEach
    public void setup() {
        mCard = new MCard();
        mCard.setId(1L);
        mCard.setBalance(50.0);
        mCard.setCardNumber("123321");
    }

    @Test
    public void testIssueNewMCard() throws Exception {
        when(mCardService.issueNewmCard("123321")).thenReturn(mCard);

        mockMvc.perform(post("/api/mcard/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("cardNumber", "123321"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mCard.getId()))
                .andExpect(jsonPath("$.balance").value(mCard.getBalance()));
    }

    @Test
    public void testGetAllMCards() throws Exception {
        when(mCardService.getAllMCards()).thenReturn(List.of(mCard));

        mockMvc.perform(get("/api/mcard/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mCard.getId()))
                .andExpect(jsonPath("$[0].balance").value(mCard.getBalance()));
    }

    @Test
    public void testTopUpMCard() throws Exception {
        double topUpAmount = 30.0;
        mCard.setBalance(mCard.getBalance() + topUpAmount);

        when(mCardService.topUpmCard(mCard.getCardNumber(), topUpAmount)).thenReturn(mCard);

        mockMvc.perform(post("/api/mcard/topup/{cardNumber}", mCard.getCardNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("amount", String.valueOf(topUpAmount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(mCard.getBalance()));
    }

    @Test
    public void testPayForRide() throws Exception {
        double fare = 2.5;
        mCard.setBalance(mCard.getBalance() - fare);

        when(mCardService.payForRide(mCard.getCardNumber(), fare)).thenReturn(mCard);

        mockMvc.perform(post("/api/mcard/pay/{cardNumber}", mCard.getCardNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fare", String.valueOf(fare)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(mCard.getBalance()));
    }

    @Test
    public void testCheckBalance() throws Exception {
        when(mCardService.checkBalance(mCard.getCardNumber())).thenReturn(mCard.getBalance());

        mockMvc.perform(get("/api/mcard/balance/{cardNumber}", mCard.getCardNumber()))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(mCard.getBalance())));
    }

    @Test
    public void testGetMCardHistory() throws Exception {
        when(mCardService.getmCardRideHistory(mCard.getCardNumber())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/mcard/history/{cardNumber}", mCard.getCardNumber()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
