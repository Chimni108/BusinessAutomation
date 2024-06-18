package com.minch.BPA.springbootcucumber.grpc;

import com.minch.account.v1alpha1.Account;
import com.minch.account.v1alpha1.AccountServiceGrpc;
import com.minch.account.v1alpha1.GetAccountRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import minch.bpa.ordering.productorder.v1alpha1.ProductOrderServiceGrpc;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Slf4j
@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class Client {
    //ManagedChannel channel;
  @GrpcClient("bpa-test")
  AccountServiceGrpc.AccountServiceBlockingStub clientStub;
  ProductOrderServiceGrpc.ProductOrderServiceBlockingStub productOrderClientStub;
    public Account getAccounts(GetAccountRequest accountRequest)
    {
        Account accountresponse =null;
        try
        {
            accountresponse=clientStub.getAccount(accountRequest);
        }
        catch (Exception e)
        {
            Status status = Status.fromThrowable(e);
            log.error("Error in response ---> Code: {}, Description: {}", status.getCode(), status.getDescription());
        }
      return accountresponse;
    }
}


