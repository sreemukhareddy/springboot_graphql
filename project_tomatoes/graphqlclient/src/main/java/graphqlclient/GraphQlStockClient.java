package graphqlclient;

import javax.xml.parsers.FactoryConfigurationError;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloSubscriptionCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport;
import com.example.GetStockEveryIntervalSubscription;
import com.example.GetStockEveryIntervalSubscription.Data;

import okhttp3.OkHttpClient;

public class GraphQlStockClient {
	
	private final ApolloClient apolloClient;

	public GraphQlStockClient(String serverEndpoint, String subscriptionEndPoint) {
		var okHttpClient = new OkHttpClient();

		apolloClient = ApolloClient.builder().serverUrl(serverEndpoint).okHttpClient(okHttpClient)
				.subscriptionTransportFactory(new WebSocketSubscriptionTransport.Factory(subscriptionEndPoint, okHttpClient)).build();
	}
	
	public void subscribeStock() {
		apolloClient.subscribe(new GetStockEveryIntervalSubscription())
		            .execute(new ApolloSubscriptionCall.Callback<GetStockEveryIntervalSubscription.Data>() {

						@Override
						public void onResponse(Response<GetStockEveryIntervalSubscription.Data> response) {
							response.getData().randomStock();
						}

						@Override
						public void onFailure(ApolloException e) {
							System.out.println("onConnected");
						}

						@Override
						public void onCompleted() {
							System.out.println("onConnected");
						}

						@Override
						public void onTerminated() {
							System.out.println("onConnected");
						}

						@Override
						public void onConnected() {
							System.out.println("onConnected");
						}
					});
	}
	
	public boolean shutdown() {
		apolloClient.disableSubscriptions();
		return true;
	}

}
