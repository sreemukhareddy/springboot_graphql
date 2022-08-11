package graphqlclient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.AddHelloMutation;
import com.example.BooksByReleasedQuery;
import com.example.BooksByReleasedQuery.BooksByReleased;
import com.example.BooksQuery;
import com.example.BooksQuery.Book;
import com.example.DeleteHelloMutation;
import com.example.ReplaceHelloTextMutation;
import com.example.type.HelloInput;

import okhttp3.OkHttpClient;

public class GraphQlBookClient {

	private final ApolloClient apolloClient;

	public GraphQlBookClient(String serverEndpoint) {
		var okHttpClient = new OkHttpClient();

		apolloClient = ApolloClient.builder().serverUrl(serverEndpoint).okHttpClient(okHttpClient).build();
	}

	public CompletableFuture<List<Book>> allBooks() {

		var result = new CompletableFuture<List<Book>>();

		var query = apolloClient.query(new BooksQuery());

		query.enqueue(new ApolloCall.Callback<BooksQuery.Data>() {

			@Override
			public void onResponse(Response<BooksQuery.Data> response) {
				result.complete(response.getData().books());
			}

			@Override
			public void onFailure(ApolloException e) {
				throw e;
			}

		});

		return result;
	}

	public CompletableFuture<List<BooksByReleased>> booksByReleased(int releaseYear, boolean releasePrintedEdition) {

		var result = new CompletableFuture<List<BooksByReleased>>();

		var query = apolloClient.query(new BooksByReleasedQuery(Input.fromNullable(releaseYear), Input.fromNullable(releasePrintedEdition)));

		query.enqueue(new ApolloCall.Callback<BooksByReleasedQuery.Data>() {

			@Override
			public void onResponse(Response<BooksByReleasedQuery.Data> response) {
				result.complete(response.getData().booksByReleased());
			}

			@Override
			public void onFailure(ApolloException e) {
				throw e;
			}

		});

		return result;
	}
	
	public CompletableFuture<AddHelloMutation.Data> addHello(HelloInput helloInput) {
		var result = new CompletableFuture<AddHelloMutation.Data>();
		var mutation = apolloClient.mutate(new AddHelloMutation(helloInput))
					;
		mutation.enqueue(new ApolloCall.Callback<AddHelloMutation.Data>() {

			@Override
			public void onResponse(Response<AddHelloMutation.Data> response) {
				result.complete(response.data());
			}

			@Override
			public void onFailure(ApolloException e) {
				throw e;
			}
		});
		
		return result;
	}
	
	public CompletableFuture<ReplaceHelloTextMutation.Data> helloReplacement(HelloInput helloInput) {
		var result = new CompletableFuture<ReplaceHelloTextMutation.Data>();
		var mutation = apolloClient.mutate(new ReplaceHelloTextMutation(helloInput))
					;
		mutation.enqueue(new ApolloCall.Callback<ReplaceHelloTextMutation.Data>() {

			@Override
			public void onResponse(Response<ReplaceHelloTextMutation.Data> response) {
				result.complete(response.data());
			}

			@Override
			public void onFailure(ApolloException e) {
				throw e;
			}
		});
		
		return result;
	}
	
	public CompletableFuture<DeleteHelloMutation.Data> deleteHello(int number) {
		var result = new CompletableFuture<DeleteHelloMutation.Data>();
		var mutation = apolloClient.mutate(new DeleteHelloMutation(number))
					;
		mutation.enqueue(new ApolloCall.Callback<DeleteHelloMutation.Data>() {

			@Override
			public void onResponse(Response<DeleteHelloMutation.Data> response) {
				result.complete(response.data());
			}

			@Override
			public void onFailure(ApolloException e) {
				throw e;
			}
		});
		
		return result;
	}
	
	public static void main(String[] args) {
		GraphQlBookClient client = new GraphQlBookClient("http://localhost:8080/graphql");
	}

}
