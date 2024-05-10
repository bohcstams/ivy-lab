package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;
  @BeforeEach
  public void init(){
    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    //when(mockSecondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    //verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Alternating(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);
    // Act
    boolean resultPrimary = ship.fireTorpedo(FiringMode.SINGLE);
    boolean resultSecondary = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, resultPrimary);
    assertEquals(true, resultSecondary);
  }

  @Test
  public void fireTorpedo_Alternating_SecondaryEmpty(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean resultPrimary = ship.fireTorpedo(FiringMode.SINGLE);
    boolean resultSecondary = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(2)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();
    assertEquals(true, resultPrimary);
    assertEquals(true, resultSecondary);
  }

  @Test
  public void fireTorpedo_Alternating_PrimaryEmpty(){
    // Arrange
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockSecondary, times(1)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_FirstEmpty(){
    when(mockPrimary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).isEmpty();
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_SecondEmpty(){
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    assertEquals(false, result);
  }
  @Test
  public void fireTorpedo_Single_PrimaryGetEmpty(){
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);
    boolean resultFirst = ship.fireTorpedo(FiringMode.SINGLE);
    when(mockPrimary.isEmpty()).thenReturn(true);
    boolean resultSecond = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockPrimary, times(2)).isEmpty();
    assertEquals(true, resultFirst);
    assertEquals(false, resultSecond);
  }

  @Test
  public void fireTorpedo_All_AllFireFalse_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_FirstFireFalse_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_SecondFireFalse_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_AllEmpty_Success(){
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_defaultBranch(){
    boolean result = ship.fireTorpedo(FiringMode.DEF);
    assertEquals(false, result);
  }

}
