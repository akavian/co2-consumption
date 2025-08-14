package sap.sustainability.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HttpUtilityTest {

  @Mock private OkHttpClient httpClientMock;

  @Mock private Call callMock;

  @InjectMocks private HttpUtility httpUtility;

  @Test
  void testFetchCoordinates_whenValidCity() throws IOException {

    Response responseMock =
        new Response.Builder()
            .request(new Request.Builder().url("http://test").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(
                ResponseBody.create(
                    "{\"features\":[{\"geometry\":{\"coordinates\":[0,0]},\"properties\":{\"locality\":\"Hamburg\"}}]}",
                    MediaType.get("application/json")))
            .build();

    when(httpClientMock.newCall(any())).thenReturn(callMock);
    when(callMock.execute()).thenReturn(responseMock);

    double[] coordinates = httpUtility.fetchCoordinates("Hamburg", "test-Key");
    assertArrayEquals(new double[] {0, 0}, coordinates);
  }

  @Test
  void testFetchCoordinates_whenInvalidCityThrowsExceptionForInvalidCity() throws IOException {
    Response responseMock =
        new okhttp3.Response.Builder()
            .request(new Request.Builder().url("http://test").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(ResponseBody.create("", MediaType.get("application/json")))
            .build();

    doReturn(callMock).when(httpClientMock).newCall(any());
    doReturn(responseMock).when(callMock).execute();

    assertThrows(
        RuntimeException.class, () -> httpUtility.fetchCoordinates("InvalidCity", "testApiKey"));
  }

  @Test
  void testfetchMatrix_whenValidCoordinates() throws IOException {

    Response responseMock =
        new Response.Builder()
            .request(new Request.Builder().url("http://test").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(ResponseBody.create("{\"distances\":[[0,5]]}", MediaType.get("application/json")))
            .build();

    doReturn(callMock).when(httpClientMock).newCall(any());
    doReturn(responseMock).when(callMock).execute();

    double distance = httpUtility.fetchMatrix(new double[] {4, 3}, new double[] {0, 0}, "test-Key");
    assertEquals(5, distance);
  }

  @Test
  void testFetchMatrix_whenInvalidResponse_throwsException() throws IOException {
    Response responseMock =
        new Response.Builder()
            .request(new Request.Builder().url("http://test").build())
            .protocol(Protocol.HTTP_1_1)
            .code(500)
            .message("")
            .body(ResponseBody.create("", MediaType.get("application/json")))
            .build();

    doReturn(callMock).when(httpClientMock).newCall(any());
    doReturn(responseMock).when(callMock).execute();

    assertThrows(
        RuntimeException.class,
        () -> httpUtility.fetchMatrix(new double[] {0, 0}, new double[] {3, 4}, "test-Key"));
  }

  @Test
  void testFetchMatrix_whenNoDrivingPathExists_throwsException() throws IOException {
    Response responseMock =
        new Response.Builder()
            .request(new Request.Builder().url("http://test").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(
                ResponseBody.create(
                    "{\"distances\":[[0,null]]}", MediaType.get("application/json")))
            .build();

    doReturn(callMock).when(httpClientMock).newCall(any());
    doReturn(responseMock).when(callMock).execute();

    assertThrows(
        RuntimeException.class,
        () -> httpUtility.fetchMatrix(new double[] {0, 0}, new double[] {3, 4}, "test-Key"));
  }
}
