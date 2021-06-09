package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.MainPage;
import ru.netology.web.page.PaymentPage;
import ru.netology.web.sqlentities.PaymentEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.netology.web.data.DataHelper.getValidApprovedCardData;
import static ru.netology.web.data.DataHelper.getValidDeclinedCardData;
import static ru.netology.web.data.SQLHelper.*;

public class HappyPathTest extends TestBase {
    MainPage mainPage = new MainPage();
    PaymentPage paymentPage = new PaymentPage();

    @Nested
    class HappyPath1OfDebitCardTests {

        @BeforeEach
        void setUpAllDebitCardTests() {
            mainPage.payWithDebitCard();
        }

        @Test
        void shouldDoPaymentWhenValidApprovedCard() {
            var paymentEntity = getPaymentEntityRecord();
            var orderEntity = getOrderPaymentLastRecord();
            paymentPage.fillForm(info);
            paymentPage.waitIfSuccessMessage();
            val expectedAmount = "4500000";
            var amount = paymentEntity.getAmount();
            assertEquals(expectedAmount,amount);
            val expectedStatus = "APPROVED";
            var status = paymentEntity.getStatus();
            assertEquals(expectedStatus,status);
            var transactionId = paymentEntity.getTransaction_id();
            assertNotNull(transactionId);
            var orderPaymentId = orderEntity.getOrderPayment_Id();
            assertNotNull(orderPaymentId);
            assertEquals(transactionId,orderPaymentId);
        }

        @Test
        void shouldNotDoPaymentWhenValidDeclinedCard() {
            var paymentEntity = getPaymentEntityRecord();
            var orderEntity = getOrderPaymentLastRecord();
            paymentPage.fillForm(info);
            paymentPage.waitIfFailMessage();
            val expectedStatus = "DECLINED";
            var StatusForPaymentWithDebitCard = paymentEntity.getStatusForPaymentWithDebitCard();
            assertEquals(expectedStatus, StatusForPaymentWithDebitCard);
            val expectedId = getPaymentId();
            assertNotNull(expectedId);
            val actualId = getOrderPaymentId();
            assertNotNull(actualId);
            assertEquals(expectedId, actualId);
        }
    }

    @Nested
    class HappyPath2OfCreditCardTests {

        @BeforeEach
        void setUpAllCreditCardTests() {
            mainPage.payWithCreditCard();
        }

        @Test
        void shouldDoPaymentWhenValidApprovedCard() {
            val info = getValidApprovedCardData();
            paymentPage.fillForm(info);
            paymentPage.waitIfSuccessMessage();
            val expectedStatus = "APPROVED";
            val actualStatus = getStatusForPaymentWithCreditCard();
            assertEquals(expectedStatus, actualStatus);
            val expectedId = getCreditId();
            assertNotNull(expectedId);
            val actualId = getOrderCreditId();
            assertNotNull(actualId);
            assertEquals(expectedId, actualId);
        }

        @Test
        void shouldNotDoPaymentWhenValidDeclinedCard() {
            val info = getValidDeclinedCardData();
            paymentPage.fillForm(info);
            paymentPage.waitIfFailMessage();
            val expectedStatus = "DECLINED";
            val actualStatus = getStatusForPaymentWithCreditCard();
            assertEquals(expectedStatus, actualStatus);
            val expectedId = getCreditId();
            assertNotNull(expectedId);
            val actualId = getOrderCreditId();
            assertNotNull(actualId);
            assertEquals(expectedId, actualId);
        }
    }
}