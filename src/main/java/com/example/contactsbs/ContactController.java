package com.example.contactsbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Map;

@Controller
public class ContactController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String index(Model model) {
        List<Map<String, Object>> contacts = jdbcTemplate.queryForList("SELECT * FROM contacts");
        model.addAttribute("contacts", contacts);
        return "index";
    }

    @PostMapping("/add")
    public String addContact(Contact contact) {
        jdbcTemplate.update("INSERT INTO contacts (name, phone, address) VALUES (?, ?, ?)",
                contact.getName(), contact.getPhone(), contact.getAddress());
        return "redirect:/";
    }
    @PostMapping("/delete")
    public String deleteContact(@RequestParam("name") String name) {
        jdbcTemplate.update("DELETE FROM contacts WHERE name = ?", name);
        return "redirect:/";
    }
    @PostMapping("/update")
    public String updateContact(@RequestParam("oldName") String oldName,
                                @RequestParam("newName") String newName,
                                @RequestParam("newPhone") String newPhone,
                                @RequestParam("newAddress") String newAddress) {
        jdbcTemplate.update("UPDATE contacts SET name = ?, phone = ?, address = ? WHERE name = ?",
                newName, newPhone, newAddress, oldName);
        return "redirect:/";
    }
}
