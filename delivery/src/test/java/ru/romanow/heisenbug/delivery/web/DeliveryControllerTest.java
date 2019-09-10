package ru.romanow.heisenbug.delivery.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.romanow.heisenbug.delivery.model.DeliveryRequest;
import ru.romanow.heisenbug.delivery.service.DeliveryManageService;

import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs.dslContract;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeliveryController.class)
@AutoConfigureRestDocs
class DeliveryControllerTest {

    @MockBean
    private DeliveryManageService deliveryManageService;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @Test
    void deliver()
            throws Exception {
        final DeliveryRequest request = new DeliveryRequest()
                .setAddress(randomAlphanumeric(10))
                .setFirstName(randomAlphabetic(10))
                .setLastName(randomAlphabetic(10));
        final UUID orderUid = UUID.randomUUID();
        mockMvc.perform(post(format("/api/v1/delivery/%s/deliver", orderUid))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(request)))
                .andExpect(status().isAccepted())
                .andDo(document("deliver",
                        requestFields(
                                fieldWithPath("address").description("Delivery address").type(JsonFieldType.STRING),
                                fieldWithPath("firstName").description("First name").type(JsonFieldType.STRING),
                                fieldWithPath("lastName").description("Last name").optional().type(JsonFieldType.STRING)
                        ),
                        dslContract())
                );

    }
}