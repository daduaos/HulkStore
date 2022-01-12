package com.hulk.store.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hulk.store.HulkStoreApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = HulkStoreApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)

public class SpringIntegrationTestBase {

}
