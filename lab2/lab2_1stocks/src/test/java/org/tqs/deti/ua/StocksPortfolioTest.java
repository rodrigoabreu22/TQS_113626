package org.tqs.deti.ua;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {
    
    @Mock
    IStockmarketService market;

    @InjectMocks
    StocksPortfolio portfolio;

    @AfterEach
    public void tearDown() {
        portfolio = null;
    }

    @Test
    public void totalValueTest(){
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);

        //this generates the warnings of question c)
        //when(market.lookUpPrice("TESLA")).thenReturn(10.0);

        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 4));

        double result = portfolio.totalValue();

        //junit
        assertEquals(14.0, result);

        //harmcrest
        assertThat(result, equalTo(14.0));

        verify(market, times(2)).lookUpPrice(anyString());
    }

    @Test
    public void AImostValuableStocksTest(){
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);
        when(market.lookUpPrice("TESLA")).thenReturn(10.0);
        when(market.lookUpPrice("APPLE")).thenReturn(3.0);
        when(market.lookUpPrice("NVIDEA")).thenReturn(5.0);

        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 4));
        portfolio.addStock(new Stock("TESLA", 2));
        portfolio.addStock(new Stock("APPLE", 4));
        portfolio.addStock(new Stock("NVIDEA", 2));

        List<Stock> topStocks = portfolio.mostValuableStocks(2);

        assertEquals(2, topStocks.size());
        assertEquals("TESLA", topStocks.get(0).getLabel());
        assertEquals("APPLE", topStocks.get(1).getLabel());
    }

    @Test
    public void myMostValuableStocksTest(){
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);
        when(market.lookUpPrice("TESLA")).thenReturn(10.0);
        when(market.lookUpPrice("APPLE")).thenReturn(3.0);
        when(market.lookUpPrice("NVIDEA")).thenReturn(5.0);

        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 4));
        portfolio.addStock(new Stock("TESLA", 2));
        portfolio.addStock(new Stock("APPLE", 4));
        portfolio.addStock(new Stock("NVIDEA", 2));

        

        assertThrows(IllegalArgumentException.class, () -> {
            portfolio.mostValuableStocks(6);
        });
            
        List<Stock> topStocks = portfolio.mostValuableStocks(2);

        assertEquals(2, topStocks.size());
        assertEquals("TESLA", topStocks.get(0).getLabel());
        assertEquals("APPLE", topStocks.get(1).getLabel());
    }
    
}
