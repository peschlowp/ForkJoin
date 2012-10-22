package benchmark.document;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import document.Document;
import document.usertags.UserTagsCalculator;
import document.usertags.UserTagsBundle;

public class UserTagsCalculatorTest {

    @Test
    public void shouldExtractUserTagsCorrectlyForOneDocumentAndOneUser() {
	String user = "user";
	String tag1 = "tag1";
	String tag2 = "tag2";
	Document[] documents = new Document[1];
	Set<String> users = createHashSet(user);
	List<String> tags = createArrayList(tag1, tag2);
	documents[0] = new Document(users, tags);

	UserTagsBundle bundle = UserTagsCalculator.compute(documents, 0, 1);

	Map<String, Set<String>> result = bundle.getUserToTags();
	assertEquals(1, result.size());
	assertNotNull(result.get(user));
	assertEquals(2, result.get(user).size());
	assertTrue(result.get(user).contains(tag1));
	assertTrue(result.get(user).contains(tag2));
    }

    @Test
    public void shouldExtractUserTagsCorrectlyIfDocumentHasNoTags() {
	String user = "user";
	Document[] documents = new Document[1];
	Set<String> users = createHashSet(user);
	documents[0] = new Document(users, new ArrayList<String>());

	UserTagsBundle bundle = UserTagsCalculator.compute(documents, 0, 1);

	Map<String, Set<String>> result = bundle.getUserToTags();
	assertEquals(1, result.size());
	assertNotNull(result.get(user));
	assertTrue(result.get(user).isEmpty());
    }

    @Test
    public void shouldExtractUserTagsCorrectlyForOneDocumentAndThreeUsers() {
	String user1 = "user1";
	String user2 = "user2";
	String user3 = "user3";
	String tag1 = "tag1";
	String tag2 = "tag2";
	Document[] documents = new Document[1];
	Set<String> users = createHashSet(user1, user2, user3);
	List<String> tags = createArrayList(tag1, tag2);
	documents[0] = new Document(users, tags);

	UserTagsBundle bundle = UserTagsCalculator.compute(documents, 0, 1);

	Map<String, Set<String>> result = bundle.getUserToTags();
	assertEquals(3, result.size());
	for (String user : users) {
	    assertNotNull(result.get(user));
	    assertEquals(2, result.get(user).size());
	    assertTrue(result.get(user).contains(tag1));
	    assertTrue(result.get(user).contains(tag2));
	}
    }

    @Test
    public void shouldExtractUserTagsCorrectlyForThreeDocumentsAndOneUser() {
	String user = "user";
	String tag1 = "tag1";
	String tag2 = "tag2";
	String tag3 = "tag3";
	String tag4 = "tag4";
	Document[] documents = new Document[3];
	documents[0] = new Document(createHashSet(user), createArrayList(tag1, tag2));
	documents[1] = new Document(createHashSet(user), createArrayList(tag2, tag3));
	documents[2] = new Document(createHashSet(user), createArrayList(tag1, tag4));

	UserTagsBundle bundle = UserTagsCalculator.compute(documents, 0, 3);

	Map<String, Set<String>> result = bundle.getUserToTags();
	assertEquals(1, result.size());
	assertNotNull(result.get(user));
	assertEquals(4, result.get(user).size());
	assertTrue(result.get(user).contains(tag1));
	assertTrue(result.get(user).contains(tag2));
	assertTrue(result.get(user).contains(tag3));
	assertTrue(result.get(user).contains(tag4));
    }

    @Test
    public void shouldExtractUserTagsCorrectlyForThreeDocumentsAndThreeUsers() {
	String user1 = "user1";
	String user2 = "user2";
	String user3 = "user3";
	String tag1 = "tag1";
	String tag2 = "tag2";
	String tag3 = "tag3";
	String tag4 = "tag4";
	Document[] documents = new Document[3];
	documents[0] = new Document(createHashSet(user1, user2), createArrayList(tag1, tag2));
	documents[1] = new Document(createHashSet(user2), createArrayList(tag2, tag3));
	documents[2] = new Document(createHashSet(user1, user2, user3), createArrayList(tag1, tag4));

	UserTagsBundle bundle = UserTagsCalculator.compute(documents, 0, 3);

	Map<String, Set<String>> result = bundle.getUserToTags();
	assertEquals(3, result.size());
	assertNotNull(result.get(user1));
	assertEquals(3, result.get(user1).size());
	assertTrue(result.get(user1).contains(tag1));
	assertTrue(result.get(user1).contains(tag2));
	assertTrue(result.get(user1).contains(tag4));
	assertNotNull(result.get(user2));
	assertEquals(4, result.get(user2).size());
	assertTrue(result.get(user2).contains(tag1));
	assertTrue(result.get(user2).contains(tag2));
	assertTrue(result.get(user2).contains(tag3));
	assertTrue(result.get(user2).contains(tag4));
	assertNotNull(result.get(user3));
	assertEquals(2, result.get(user3).size());
	assertTrue(result.get(user3).contains(tag1));
	assertTrue(result.get(user3).contains(tag4));
    }

    private HashSet<String> createHashSet(String... elements) {
	HashSet<String> set = new HashSet<>();
	for (String element : elements) {
	    set.add(element);
	}
	return set;
    }

    private ArrayList<String> createArrayList(String... elements) {
	ArrayList<String> list = new ArrayList<>();
	for (String element : elements) {
	    list.add(element);
	}
	return list;
    }

    @Test
    public void shouldReduceUserTagsCorrectly() {
	String user1 = "user1";
	String user2 = "user2";
	String user3 = "user3";
	String tag1 = "tag1";
	String tag2 = "tag2";
	String tag3 = "tag3";
	Map<String, Set<String>> map1 = new HashMap<>();
	map1.put(user1, createHashSet(tag1));
	map1.put(user2, createHashSet(tag1, tag2));
	Map<String, Set<String>> map2 = new HashMap<>();
	map2.put(user2, createHashSet(tag1, tag3));
	map2.put(user3, createHashSet(tag2, tag3));
	UserTagsBundle bundle1 = new UserTagsBundle(map1);
	UserTagsBundle bundle2 = new UserTagsBundle(map2);

	UserTagsBundle bundle = UserTagsCalculator.reduce(bundle1, bundle2);

	Map<String, Set<String>> result = bundle.getUserToTags();
	assertEquals(3, result.size());
	assertNotNull(result.get(user1));
	assertEquals(1, result.get(user1).size());
	assertTrue(result.get(user1).contains(tag1));
	assertNotNull(result.get(user2));
	assertEquals(3, result.get(user2).size());
	assertTrue(result.get(user2).contains(tag1));
	assertTrue(result.get(user2).contains(tag2));
	assertTrue(result.get(user2).contains(tag3));
	assertNotNull(result.get(user3));
	assertEquals(2, result.get(user3).size());
	assertTrue(result.get(user3).contains(tag2));
	assertTrue(result.get(user3).contains(tag3));
    }
}
