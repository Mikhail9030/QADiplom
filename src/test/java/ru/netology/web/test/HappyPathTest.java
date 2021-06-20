package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.MainPage;
import ru.netology.web.page.PaymentPage;

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
            val info = getValidApprovedCardData();
            paymentPage.fillForm(info);
            paymentPage.waitIfFailMessage();
            val expectedAmount = "4500000";
            val expectedStatus = "APPROVED";
            var orderEntity = getOrderPaymentLastRecord();
            var paymentEntity = getPaymentEntityRecord();
            var amount = paymentEntity.getAmount();
            assertEquals(expectedAmount,amount);
            var status = paymentEntity.getStatus();
            assertEquals(expectedStatus,status);
            var transactionId = paymentEntity.getTransaction_id();
            assertNotNull(transactionId);
            var orderPaymentId = orderEntity.getPayment_id();
            assertNotNull(orderPaymentId);
            assertEquals(transactionId,orderPaymentId);
        }

        @Test
        void shouldNotDoPaymentWhenValidDeclinedCard() {
            val info = getValidDeclinedCardData();
            paymentPage.fillForm(info);
            paymentPage.waitIfFailMessage();
            val expectedStatus = "DECLINED";
            var orderEntity = getOrderPaymentLastRecord();
            var paymentEntity = getPaymentEntityRecord();
            var actualStatus = paymentEntity.getStatus();
            assertEquals(expectedStatus, actualStatus);
            var expectedId = orderEntity.getId();
            assertNotNull(expectedId);
            var actualId = orderEntity.getId();
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
            var creditRequestEntity = getCreditRequestEntityRecord();
            var actualStatus = creditRequestEntity.getCreated();
            assertEquals(expectedStatus, actualStatus);
            var expectedId = creditRequestEntity.getId();
            assertNotNull(expectedId);
            var actualId = creditRequestEntity.getBank_id();
            assertNotNull(actualId);
            assertEquals(expectedId, actualId);
        }

        @Test
        void shouldNotDoPaymentWhenValidDeclinedCard() {
            val info = getValidDeclinedCardData();
            paymentPage.fillForm(info);
            paymentPage.waitIfFailMessage();
            val expectedStatus = "DECLINED";
            var creditRequestEntity = getCreditRequestEntityRecord();
            var actualStatus = creditRequestEntity.getStatus();
            assertEquals(expectedStatus, actualStatus);
            var expectedId = creditRequestEntity.getId();
            assertNotNull(expectedId);
            var actualId = creditRequestEntity.getId();
            assertNotNull(actualId);
            assertEquals(expectedId, actualId);
        }
    }
}