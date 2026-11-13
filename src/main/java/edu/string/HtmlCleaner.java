package edu.string;

/**
 * This class will remove basic html code from a string.
 * Some instructions are not taken into account : links, tables.
 */
public class HtmlCleaner {
    public HtmlCleaner() {
        // do nothing
    }

    public String process(String textWithHtml) {
        String result = textWithHtml;
        result = removeColorTags(result);
        result = removeLineBreaks(result);
        result = removeBasicTags(result);
        result = removeTagsWithStyle(result);
        result = handleLists(result);
        return result;
    }

    private String removeBasicTags(String html) {
        String result = html;
        result = result.replaceAll("&nbsp;", "\t");
        result = result.replaceAll("(<b>)|(</b>)", "");
        result = result.replaceAll("(<i>)|(</i>)", "");
        result = result.replaceAll("(<u>)|(</u>)", "");
        result = result.replaceAll("(<p>)|(</p>)", "");
        result = result.replaceAll("(<em>)|(</em>)", "");
        result = result.replaceAll("(<strong>)|(</strong>)", "");
        return result;
    }

    private String removeLineBreaks(String html) {
        return html.replaceAll("(<br/>)|(</br>)|(<br />)|(< /br>)|(<br>)", "\n");
    }

    private String handleLists(String html) {
        final String LINE_BREAK_AND_DASH = "\n\t- ";
        String result = html;
        result = result.replaceAll("(<ul>)|(<ol>)", "");
        result = result.replaceAll("(</ul>)|(</ol>)", "\n");
        result = result.replaceAll("<li data-list=\"[a-zA-Z]*\">", LINE_BREAK_AND_DASH);
        result = result.replaceAll("<li data-list=\"[a-zA-Z]*\" class=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", LINE_BREAK_AND_DASH);
        result = result.replaceAll("<li class=\"[\\-\\_():,;a-zA-Z0-9 ]*\" data-list=\"[a-zA-Z]*\">", LINE_BREAK_AND_DASH);
        result = result.replaceAll("<li>", LINE_BREAK_AND_DASH);
        result = result.replaceAll("</li>", "");
        return result;
    }

    /**
     * This method will catch up with remaining html code that would have made through removeColorTags().
     * @param html text with html code related to style or class on tags : strong, p, span, em
     * @return text without that html code
     */
    private String removeTagsWithStyle(String html) {
        String result = html;
        result = result.replaceAll("<strong style=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("<strong class=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("</strong>", "");
        result = result.replaceAll("<p style=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("<p class=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("</p>", "");
        result = result.replaceAll("<span style=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("<span class=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("<span style=\"[\\-\\_():,;a-zA-Z0-9 ]*\" contenteditable=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("<span class=\"[\\-\\_():,;a-zA-Z0-9 ]*\" contenteditable=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("</span>", "");
        result = result.replaceAll("<em style=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("<em class=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("</em>", "");
        result = result.replaceAll("<div style=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("<div class=\"[\\-\\_():,;a-zA-Z0-9 ]*\">", "");
        result = result.replaceAll("</div>", "");

        return result;
    }

    /**
     * Remove <span> tag with color info, trying every possible combination, same <em> tag
     * @param html text with html code to clean
     */
    private String removeColorTags(String html) {
        String result = html;
        result = result.replaceAll("<span style=\"color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<span style=\"background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<span style=\"color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\); background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<span style=\"background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\); color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<span style=\"color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<span style=\"background-color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<span style=\"color: [a-zA-Z ]*; background-color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<span style=\"background-color: [a-zA-Z ]*; color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<span style=\"color: [a-zA-Z ]*; background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<span style=\"color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\); background-color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<span style=\"background-color: [a-zA-Z ]*; color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<span style=\"background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\); color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("</span>", "");

        result = result.replaceAll("<em style=\"color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<em style=\"background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<em style=\"color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\); background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<em style=\"background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\); color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<em style=\"color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<em style=\"background-color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<em style=\"color: [a-zA-Z ]*; background-color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<em style=\"background-color: [a-zA-Z ]*; color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<em style=\"color: [a-zA-Z ]*; background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<em style=\"color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\); background-color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("<em style=\"background-color: [a-zA-Z ]*; color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\);\">", "");
        result = result.replaceAll("<em style=\"background-color: rgb\\([0-9 ]*,[0-9 ]*,[0-9 ]*\\); color: [a-zA-Z ]*;\">", "");
        result = result.replaceAll("</em", "");
        return result;
    }

}