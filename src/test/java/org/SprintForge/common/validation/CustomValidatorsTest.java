package org.SprintForge.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.SprintForge.common.validation.annotation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomValidatorsTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Helper Beans
    static class PasswordBean {
        @ValidPassword
        String password;
    }

    static class StrongPasswordBean {
        @StrongPassword
        String password;
    }

    static class UsernameBean {
        @ValidUsername
        String username;
    }

    static class TrimmedBean {
        @Trimmed
        String text;
    }

    static class NoHtmlBean {
        @NoHtml
        String text;
    }

    static class NoScriptBean {
        @NoScript
        String text;
    }

    static class SafeTextBean {
        @SafeText
        String text;
    }

    static class HexColorBean {
        @HexColor
        String color;
    }

    static class SlugBean {
        @Slug
        String slug;
    }

    static class ValidNameBean {
        @ValidName
        String name;
    }

    static class MarkdownBean {
        @Markdown
        String markdown;
    }

    static class PlainTextBean {
        @PlainText
        String text;
    }

    static class FutureOrPresentBean {
        @FutureOrPresentDate
        LocalDate date;
    }

    @StartBeforeEnd(startDateField = "start", endDateField = "end")
    static class DateRangeBean {
        LocalDate start;
        LocalDate end;
        DateRangeBean(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
        }
    }

    @NotPastDeadline(targetField = "target", deadlineField = "deadline")
    static class DeadlineClassBean {
        LocalDate target;
        LocalDate deadline;
        DeadlineClassBean(LocalDate target, LocalDate deadline) {
            this.target = target;
            this.deadline = deadline;
        }
    }

    static class DeadlineFieldBean {
        @NotPastDeadline
        LocalDate date;
    }

    static class FileTypeBean {
        @AllowedFileType(allowedTypes = {".png", "image/jpeg"})
        MultipartFile file;
        FileTypeBean(MultipartFile file) {
            this.file = file;
        }
    }

    static class FileSizeBean {
        @MaxFileSize("1MB")
        MultipartFile file;
        FileSizeBean(MultipartFile file) {
            this.file = file;
        }
    }

    static class ImageOnlyBean {
        @ImageOnly
        MultipartFile file;
        ImageOnlyBean(MultipartFile file) {
            this.file = file;
        }
    }

    static class UniqueElementsBean {
        @UniqueElements
        List<String> list;
    }

    static class NoDuplicateIdsBean {
        @NoDuplicateIds(idField = "id")
        List<TestItem> list;
    }

    static class MaxCollectionSizeBean {
        @MaxCollectionSize(2)
        List<String> list;
    }

    static class EnumBean {
        @ValidEnum(enumClass = TestEnum.class, ignoreCase = true)
        String value;
    }

    static class UrlBean {
        @ValidUrl
        String url;
    }

    static class DomainBean {
        @ValidDomain
        String domain;
    }

    static class TimezoneBean {
        @ValidTimezone
        String zoneId;
    }

    static class CronBean {
        @ValidCron
        String cron;
    }

    static class TestItem {
        private final Long id;
        TestItem(Long id) { this.id = id; }
        public Long getId() { return id; }
    }

    enum TestEnum {
        CREATE, UPDATE, DELETE
    }

    // Test cases
    @Test
    void testValidPassword() {
        PasswordBean bean = new PasswordBean();
        bean.password = "123456";
        assertTrue(validator.validate(bean).isEmpty());

        bean.password = "12345"; // too short
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testStrongPassword() {
        StrongPasswordBean bean = new StrongPasswordBean();
        bean.password = "P@ssword1"; // Strong
        assertTrue(validator.validate(bean).isEmpty());

        bean.password = "password"; // Weak
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testValidUsername() {
        UsernameBean bean = new UsernameBean();
        bean.username = "john_doe-123";
        assertTrue(validator.validate(bean).isEmpty());

        bean.username = "admin"; // Reserved
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testTrimmed() {
        TrimmedBean bean = new TrimmedBean();
        bean.text = "trimmed text";
        assertTrue(validator.validate(bean).isEmpty());

        bean.text = " leading space";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testNoHtml() {
        NoHtmlBean bean = new NoHtmlBean();
        bean.text = "Plain text";
        assertTrue(validator.validate(bean).isEmpty());

        bean.text = "<div>html</div>";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testNoScript() {
        NoScriptBean bean = new NoScriptBean();
        bean.text = "alert('hello')";
        assertTrue(validator.validate(bean).isEmpty());

        bean.text = "<script>alert('hello')</script>";
        assertFalse(validator.validate(bean).isEmpty());

        bean.text = "javascript:alert(1)";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testSafeText() {
        SafeTextBean bean = new SafeTextBean();
        bean.text = "Safe content";
        assertTrue(validator.validate(bean).isEmpty());

        bean.text = "<p>unsafe</p>";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testHexColor() {
        HexColorBean bean = new HexColorBean();
        bean.color = "#FFF";
        assertTrue(validator.validate(bean).isEmpty());
        bean.color = "#1a2B3c";
        assertTrue(validator.validate(bean).isEmpty());

        bean.color = "123456"; // Missing #
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testSlug() {
        SlugBean bean = new SlugBean();
        bean.slug = "my-awesome-slug-123";
        assertTrue(validator.validate(bean).isEmpty());

        bean.slug = "invalid_slug";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testValidName() {
        ValidNameBean bean = new ValidNameBean();
        bean.name = "John O'Connor-Smith";
        assertTrue(validator.validate(bean).isEmpty());

        bean.name = "John123";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testMarkdown() {
        MarkdownBean bean = new MarkdownBean();
        bean.markdown = "# Header\n**bold**";
        assertTrue(validator.validate(bean).isEmpty());

        bean.markdown = "[click](javascript:alert(1))";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testPlainText() {
        PlainTextBean bean = new PlainTextBean();
        bean.text = "Hello world";
        assertTrue(validator.validate(bean).isEmpty());

        bean.text = "**Hello**";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testFutureOrPresentDate() {
        FutureOrPresentBean bean = new FutureOrPresentBean();
        bean.date = LocalDate.now().plusDays(1);
        assertTrue(validator.validate(bean).isEmpty());

        bean.date = LocalDate.now().minusDays(1);
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testStartBeforeEnd() {
        DateRangeBean valid = new DateRangeBean(LocalDate.now(), LocalDate.now().plusDays(2));
        assertTrue(validator.validate(valid).isEmpty());

        DateRangeBean invalid = new DateRangeBean(LocalDate.now().plusDays(2), LocalDate.now());
        assertFalse(validator.validate(invalid).isEmpty());
    }

    @Test
    void testNotPastDeadlineClassLevel() {
        DeadlineClassBean valid = new DeadlineClassBean(LocalDate.now(), LocalDate.now().plusDays(2));
        assertTrue(validator.validate(valid).isEmpty());

        DeadlineClassBean invalid = new DeadlineClassBean(LocalDate.now().plusDays(2), LocalDate.now());
        assertFalse(validator.validate(invalid).isEmpty());
    }

    @Test
    void testNotPastDeadlineFieldLevel() {
        DeadlineFieldBean bean = new DeadlineFieldBean();
        bean.date = LocalDate.now().plusDays(1);
        assertTrue(validator.validate(bean).isEmpty());

        bean.date = LocalDate.now().minusDays(1);
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testAllowedFileType() {
        MockMultipartFile pngFile = new MockMultipartFile("file", "test.png", "image/png", new byte[10]);
        FileTypeBean validBean = new FileTypeBean(pngFile);
        assertTrue(validator.validate(validBean).isEmpty());

        MockMultipartFile txtFile = new MockMultipartFile("file", "test.txt", "text/plain", new byte[10]);
        FileTypeBean invalidBean = new FileTypeBean(txtFile);
        assertFalse(validator.validate(invalidBean).isEmpty());
    }

    @Test
    void testMaxFileSize() {
        MockMultipartFile smallFile = new MockMultipartFile("file", "test.png", "image/png", new byte[500]);
        FileSizeBean validBean = new FileSizeBean(smallFile);
        assertTrue(validator.validate(validBean).isEmpty());

        // 1.5 MB in bytes (1.5 * 1024 * 1024)
        MockMultipartFile largeFile = new MockMultipartFile("file", "test.png", "image/png", new byte[2 * 1024 * 1024]);
        FileSizeBean invalidBean = new FileSizeBean(largeFile);
        assertFalse(validator.validate(invalidBean).isEmpty());
    }

    @Test
    void testImageOnly() {
        MockMultipartFile pngFile = new MockMultipartFile("file", "test.png", "image/png", new byte[10]);
        ImageOnlyBean validBean = new ImageOnlyBean(pngFile);
        assertTrue(validator.validate(validBean).isEmpty());

        MockMultipartFile txtFile = new MockMultipartFile("file", "test.txt", "text/plain", new byte[10]);
        ImageOnlyBean invalidBean = new ImageOnlyBean(txtFile);
        assertFalse(validator.validate(invalidBean).isEmpty());
    }

    @Test
    void testUniqueElements() {
        UniqueElementsBean bean = new UniqueElementsBean();
        bean.list = Arrays.asList("a", "b", "c");
        assertTrue(validator.validate(bean).isEmpty());

        bean.list = Arrays.asList("a", "b", "a");
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testNoDuplicateIds() {
        NoDuplicateIdsBean bean = new NoDuplicateIdsBean();
        bean.list = Arrays.asList(new TestItem(1L), new TestItem(2L));
        assertTrue(validator.validate(bean).isEmpty());

        bean.list = Arrays.asList(new TestItem(1L), new TestItem(1L));
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testMaxCollectionSize() {
        MaxCollectionSizeBean bean = new MaxCollectionSizeBean();
        bean.list = Arrays.asList("a", "b");
        assertTrue(validator.validate(bean).isEmpty());

        bean.list = Arrays.asList("a", "b", "c");
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testValidEnum() {
        EnumBean bean = new EnumBean();
        bean.value = "CREATE";
        assertTrue(validator.validate(bean).isEmpty());

        bean.value = "create"; // ignoreCase is true
        assertTrue(validator.validate(bean).isEmpty());

        bean.value = "INVALID";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testValidUrl() {
        UrlBean bean = new UrlBean();
        bean.url = "https://example.com/test";
        assertTrue(validator.validate(bean).isEmpty());

        bean.url = "invalid-url";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testValidDomain() {
        DomainBean bean = new DomainBean();
        bean.domain = "example.com";
        assertTrue(validator.validate(bean).isEmpty());

        bean.domain = "example.com/path";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testValidTimezone() {
        TimezoneBean bean = new TimezoneBean();
        bean.zoneId = "UTC";
        assertTrue(validator.validate(bean).isEmpty());
        bean.zoneId = "America/New_York";
        assertTrue(validator.validate(bean).isEmpty());

        bean.zoneId = "InvalidTimezone";
        assertFalse(validator.validate(bean).isEmpty());
    }

    @Test
    void testValidCron() {
        CronBean bean = new CronBean();
        bean.cron = "*/5 * * * * *"; // Spring CronExpression (6 fields)
        assertTrue(validator.validate(bean).isEmpty());

        bean.cron = "invalid cron";
        assertFalse(validator.validate(bean).isEmpty());
    }
}
