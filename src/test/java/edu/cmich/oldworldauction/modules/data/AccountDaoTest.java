package edu.cmich.oldworldauction.modules.data;

import edu.cmich.oldworldauction.modules.models.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link AccountDao} class.
 *
 * @author Brock Jones
 */
public class AccountDaoTest {
    private static final AccountDao accountDao = new AccountDao();

    @Test
    public void nullCardNumberThrowsIllegalArgumentException() {
        PaymentMethod paymentMethod = new PaymentMethod(34, true, null, 5, 26, 334, "");

        assertThrows(IllegalArgumentException.class, () -> accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -3, -45, -99, -150})
    public void invalidPaymentMethodIdThrowsIllegalArgumentException(int id) {
        PaymentMethod paymentMethod = new PaymentMethod(id, false, "1111-2222-3333-4444", 5, 26, 887, "");

        assertThrows(IllegalArgumentException.class, () -> accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 45, 99, 150})
    public void validPaymentMethodIdReturnsTrue(int id) {
        PaymentMethod paymentMethod = new PaymentMethod(id, true, "1111-2222-3333-4444", 5, 26, 887, "");

        assertTrue(accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(strings = {"111-111-111-111", "1111222233334444", "num1-num2-num3-num4", "CardNumber", "1111*2222*3333*4444"})
    public void invalidPaymentMethodCardNumberThrowsIllegalArgumentException(String number) {
        PaymentMethod paymentMethod = new PaymentMethod(5, true, number, 5, 26, 887, "");

        assertThrows(IllegalArgumentException.class, () -> accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3333-4444-6666-5555", "1234-4321-5678-8765", "3333-3333-3333-3333", "5656-7887-9009-5544", "5678-9123-6543-2345"})
    public void validPaymentMethodCardNumberReturnsTrue(String number) {
        PaymentMethod paymentMethod = new PaymentMethod(5, false, number, 5, 26, 887, "");

        assertTrue(accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(ints = {-12, -6, 0, 13, 25})
    public void invalidPaymentMethodExpirationMonthThrowsIllegalArgumentException(int month) {
        PaymentMethod paymentMethod = new PaymentMethod(46, false, "1111-2222-3333-4444", month, 26, 987, "");

        assertThrows(IllegalArgumentException.class, () -> accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 6, 9, 12})
    public void validPaymentMethodExpirationMonthReturnsTrue(int month) {
        PaymentMethod paymentMethod = new PaymentMethod(45, true, "1211-2232-3343-1244", month, 26, 867, "");

        assertTrue(accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(ints = {-300, -45, -12, 136, 2023})
    public void invalidPaymentMethodExpirationYearThrowsIllegalArgumentException(int year) {
        PaymentMethod paymentMethod = new PaymentMethod(46, false, "1111-2222-3333-4444", 5, year, 987, "");

        assertThrows(IllegalArgumentException.class, () -> accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 25, 50, 75, 99})
    public void validPaymentMethodExpirationYearReturnsTrue(int year) {
        PaymentMethod paymentMethod = new PaymentMethod(45, true, "1211-2232-3343-1244", 5, year, 867, "");

        assertTrue(accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(ints = {-333, 12, 96, 12345, 567890})
    public void invalidPaymentMethodCvvThrowsIllegalArgumentException(int cvv) {
        PaymentMethod paymentMethod = new PaymentMethod(46, false, "1111-2222-3333-4444", 5, 26, cvv, "");

        assertThrows(IllegalArgumentException.class, () -> accountDao.isMethodValid(paymentMethod));
    }

    @ParameterizedTest
    @ValueSource(ints = {111, 3333, 612, 978, 1245})
    public void validPaymentMethodCvvReturnsTrue(int cvv) {
        PaymentMethod paymentMethod = new PaymentMethod(45, true, "1211-2232-3343-1244", 5, 26, cvv, "");

        assertTrue(accountDao.isMethodValid(paymentMethod));
    }
}
