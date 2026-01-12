package com.example.exploresafely;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {
    private static final Map<String, String> responses = new HashMap<>();

    static {
            responses.put("hi", "Hi buddy! How can I help you?");
            // Health-related responses
            responses.put("fever", "Take paracetamol (500mg) every 6 hours, stay hydrated, and get plenty of rest. Eat light foods like soups, boiled rice, and fruits like oranges and bananas for energy and recovery.");
            responses.put("cold", "Drink warm fluids, take vitamin C-rich foods (oranges, kiwi, tomatoes), and try steam inhalation. If symptoms persist, consider antihistamines.");
            responses.put("stomach pain", "Drink plenty of water, eat light and non-spicy foods like bananas, rice, toast, and boiled potatoes. Avoid dairy, caffeine, and oily foods.");
            responses.put("headache", "Rest in a dark, quiet room. Drink plenty of water, as dehydration can cause headaches. Foods rich in magnesium like almonds, spinach, and avocados can help.");
            responses.put("low oxygen", "Move to a well-ventilated area or higher ground if at a high altitude. Breathe deeply, stay calm, and if available, use an oxygen mask. Eating iron-rich foods like spinach, lentils, and red meat can help improve oxygen levels over time.");
            responses.put("altitude sickness", "If feeling dizzy or nauseous at high altitudes, move to a lower altitude, drink water, and rest. Avoid alcohol and take acetazolamide if prescribed.");

            // First aid and medical safety
            responses.put("first aid", "Clean wounds with antiseptic, apply an antibiotic ointment, and cover with a sterile bandage. If the wound is deep, seek medical attention immediately.");
            responses.put("burns", "For minor burns, run cool (not cold) water over the area for 10-15 minutes. Apply aloe vera or a burn ointment. Avoid popping blisters.");
            responses.put("allergies", "Avoid known allergens. If you experience a reaction, take antihistamines. Severe allergic reactions may require an epinephrine shot (EpiPen). Wear a medical alert bracelet if needed.");
            responses.put("food poisoning", "Stay hydrated, drink oral rehydration solution (ORS), and eat plain foods like rice, toast, and bananas. Avoid greasy or spicy foods.");

            // Skin care tips
            responses.put("skin care", "Apply sunscreen (SPF 30 or higher) daily, even on cloudy days. Stay hydrated, moisturize regularly, and avoid excessive sun exposure. Carry lip balm and aloe vera gel to soothe sunburns.");
            responses.put("sunburn", "Apply aloe vera or a cool compress to affected areas. Drink plenty of water and avoid direct sunlight until the skin heals.");
            responses.put("dry skin", "Use a moisturizer with hyaluronic acid or glycerin. Avoid long hot showers, and drink at least 8 glasses of water daily.");
            responses.put("bug bites", "Apply calamine lotion or aloe vera. Use insect repellent, wear long sleeves, and avoid stagnant water areas.");

            // Mental health support
            responses.put("stress relief", "Take deep breaths, practice meditation, and get enough sleep. Listening to music or journaling can also help.");
            responses.put("feeling lonely", "Try talking to a fellow traveler, video call a friend, or engage in local activities to feel connected.");
            responses.put("anxiety", "Take slow, deep breaths. Distract yourself with calming activities like listening to music or walking in nature.");

            // Food safety and hydration
            responses.put("food safety", "Eat at clean places, avoid raw street food, and drink only bottled or filtered water. Carry digestive enzymes if you have a sensitive stomach.");
            responses.put("hydration", "Drink at least 2-3 liters of clean water daily. Coconut water and herbal teas are great for hydration.");
            responses.put("energy-boosting foods", "Eat bananas, almonds, dark chocolate, and yogurt for an instant energy boost.");

            // Plants to avoid (Toxic plants)
            responses.put("plants to avoid", "Avoid touching or consuming these plants: \n1. Poison Ivy – Causes skin irritation.\n2. Deadly Nightshade – Highly toxic.\n3. Castor Bean – Seeds are poisonous.\n4. Oleander – Can be fatal if ingested.\n5. Hemlock – Extremely poisonous.");

            // Travel hygiene tips
            responses.put("hygiene tips", "Wash hands regularly, use hand sanitizer, and avoid touching your face. Keep wet wipes and tissues handy.");
            responses.put("sanitation", "Always use clean toilets, carry personal hygiene wipes, and dispose of waste properly.");

            // Solo travel safety
            responses.put("safety precautions", "1. Stay aware of your surroundings. \n2. Keep emergency contacts saved. \n3. Share your live location with a trusted person. \n4. Avoid walking alone at night in unfamiliar places. \n5. Carry a personal safety alarm or whistle.");
            responses.put("money safety", "Use a money belt or hidden pouch for cash and cards. Avoid carrying large amounts of money.");
            responses.put("fire safety", "Know emergency exits in your accommodation. Avoid overloading power sockets, and never leave candles unattended.");
            responses.put("water safety", "Avoid swimming in unknown waters. Check for signs of strong currents, and never swim alone.");
            responses.put("emergency", "In case of an emergency, contact the nearest embassy or local authorities. Keep an offline map and emergency numbers handy.");
            responses.put("disaster preparedness", "Check weather updates regularly. Keep an emergency kit with non-perishable food, water, flashlight, and a first aid kit. Identify safe shelter locations in advance.");

            // Sleep tips
            responses.put("sleep tips", "Maintain a regular sleep schedule. Use an eye mask and earplugs if you're in a noisy area. Avoid caffeine before bedtime.");
            responses.put("jet lag", "Adapt to the local time zone by getting sunlight exposure and staying hydrated. Avoid heavy meals before sleeping.");

            // Wildlife and insect safety
            responses.put("snake bite", "Stay calm and avoid sudden movements. Keep the bitten limb immobilized and below heart level. Do NOT try to suck the venom or apply ice. Seek medical help immediately. If possible, take a picture of the snake for identification.");
            responses.put("scorpion sting", "Wash the area with soap and water. Apply a cool compress to reduce swelling and pain. If symptoms worsen (difficulty breathing, severe pain), seek medical help immediately.");
            responses.put("wild animal encounter", "Do not run. Stay calm and slowly back away. Avoid direct eye contact with predators like big cats. Make yourself look bigger and make noise if necessary.");
            responses.put("bee or wasp sting", "Remove the stinger by scraping with a card (avoid squeezing it). Apply ice and take an antihistamine to reduce swelling.");

            // Forest and jungle safety
            responses.put("getting lost in a forest", "Stay where you are to avoid getting more lost. Look for high ground to signal for help. Find a water source and build a shelter using branches and leaves.");
            responses.put("safe water sources", "Running water from a stream is safer than stagnant water. Always purify water by boiling, filtering, or using purification tablets.");
            responses.put("avoiding poisonous plants", "Avoid plants with white or yellow berries, milky sap, or a strong odor. When in doubt, do NOT eat unknown plants.");

            // Mountain and valley safety
            responses.put("rockslide safety", "If rocks start falling, take cover under a large boulder or move to the side of the slope. Never stand directly under a rockfall zone.");
            responses.put("hypothermia", "Wear layered clothing, stay dry, and avoid wind exposure. If signs appear (shivering, confusion), move to a warm place and drink warm fluids.");
            responses.put("high altitude sickness", "If feeling dizzy or nauseous, descend to a lower altitude, rest, and drink water. Avoid alcohol and smoking.");

            // Survival in extreme weather
            responses.put("thunderstorm safety", "Avoid standing under trees or open fields. Stay low to the ground but do not lie flat. If inside, unplug electronic devices.");
            responses.put("heatstroke", "Move to shade, drink water, and place a cool cloth on your head and neck. If symptoms like dizziness or nausea persist, seek medical help.");
            responses.put("frostbite", "Warm the affected area with body heat (hands, armpits). Do not rub or use direct heat. Seek medical attention for severe cases.");

            // River and swamp safety
            responses.put("crossing a river", "Look for shallow areas with slow-moving water. Use a walking stick to check depth and cross facing upstream.");
            responses.put("quicksand escape", "Stay calm and avoid sudden movements. Lean back to spread your weight and slowly wiggle your legs to free them.");




    }

    public static String getResponse(String query) {
        query = query.toLowerCase();
        for (String key : responses.keySet()) {
            if (query.contains(key)) {
                return responses.get(key);
            }
        }
        return "I'm sorry, I don't have information on that. Try searching online.";
    }
}
