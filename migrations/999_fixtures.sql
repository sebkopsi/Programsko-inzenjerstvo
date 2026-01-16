

INSERT INTO public."difficultyLevel" ("name") 
VALUES 
  ('beginner');
INSERT INTO public."instructorProfile" (
  biography, username, specialization
) 
VALUES 
  (
    'Lorem ', 'Josip Josipovic', 'Croatian Cuisine'
  );
INSERT INTO public."enrolleeProfile" (username, "skillLevelId") 
VALUES 
  ( 'test', 1);

INSERT INTO public."enrolleeProfile" (
  username, "skillLevelId"
) 
VALUES 
  ('instructor', 1);


INSERT INTO public."tag" (
  name, category
)
VALUES
  ('cooking', 'general'),
  ('italian', 'general'),
  ('pasta', 'general'),
  ('gluten', 'general'),
  ('sauces', 'general');


INSERT INTO public."user" (
   firstname, surname, password_hash, 
  email, "createdAt", "enrolleeId", 
  "instructorId", "isVerified"
)
VALUES 
  (
     'test', 'test', '$argon2id$v=19$m=8192,t=3,p=1$A7Vw2BZ7D4o2YADMheJe2g$RpPi3ozewwv/ebhqgr92qD1ccckNvj8QXNFpoi3ai3c', 
    'test@testmail.com', '2026-01-15 19:11:48.945', 
    1, NULL, false
  ),
  (
    'instructor', 'instructor', '$argon2id$v=19$m=8192,t=3,p=1$DdjjsyoOYhjzjdPMXeGc1w$xztykIEvw1hq3nG/0l5SaogiR8x/Isv/ZQ6TWyKeqTA', 
    'instructor@testmail.com', '2026-01-15 19:18:40.723', 
    2, 1, false
  );

INSERT INTO public.course (
   "name", "creatorId", description, 
  "difficultyId", "avgRating"
) 
VALUES 
  (
    'Mastering Italian Cooking', 2, 'Learn the fundamentals of Italian cuisine, from classic pasta dishes to traditional desserts, with step-by-step instructions.', 
    1, 0.00
  );

INSERT INTO public."courseTag" (
  "courseId", "tagId"
)
VALUES
  (1,1),
  (1,2),
  (1,3),
  (1,4),
  (1,5);

INSERT INTO public."module" ("name", "courseId", orderindex) 
VALUES 
  ('Pasta Perfection', 1, 1);
INSERT INTO public.lecture (
  "writtenSteps", "preparationTime", 
  "cookingTime", "name", "moduleId", 
  "videoId", "difficultyId", "creatorId", 
  quizjson, minscore
) 
VALUES 
  (
    '# Classic Spaghetti with Tomato Sauce
**Preparation Time:** 10 minutes  
**Cooking Time:** 15 minutes  
**Difficulty:** Intermediate  

---

## Step-by-Step Instructions

### Step 1: Prepare the Water and Pasta
Begin by filling a large pot with fresh water and bringing it to a rolling boil over high heat. Once the water is bubbling, add a generous pinch of salt to season it. This step is crucial, as the salted water will enhance the flavor of the pasta from the inside out. Gently add the pasta to the boiling water and stir occasionally to prevent it from sticking. Cook the pasta according to the package instructions, ensuring it reaches a perfect al dente texture — tender but still slightly firm in the center.  

### Step 2: Prepare the Sauce Base
While the pasta is cooking, take a medium-sized pan and heat a couple of tablespoons of extra virgin olive oil over medium heat. Add finely chopped garlic and sauté until it releases a fragrant aroma, being careful not to let it burn. The garlic should become golden and slightly soft, creating a flavorful base for the sauce.  

### Step 3: Simmer the Tomato Sauce
Once the garlic is ready, pour in fresh or canned tomato sauce and stir to combine. Allow the sauce to simmer gently on low heat for about 10 minutes. This step is essential to let the flavors meld together — the acidity of the tomatoes softens, the garlic infuses, and the sauce thickens slightly. Taste periodically and season with a pinch of salt and a dash of freshly ground black pepper, adjusting to your preference.  

### Step 4: Combine Pasta and Sauce
When the pasta reaches the desired texture, carefully drain it, reserving a small cup of the pasta water. Add the drained pasta directly into the simmering sauce and toss thoroughly to coat every strand. If the sauce feels too thick, add a few tablespoons of the reserved pasta water to loosen it and enhance its creaminess.  

### Step 5: Serve and Garnish
Plate the pasta while it is still hot. Sprinkle with freshly grated Parmesan cheese and, if desired, add a few fresh basil leaves for aroma and presentation. Serve immediately for the best experience, pairing it with a light salad or a slice of crusty bread. Enjoy the rich, comforting flavors of this classic Italian dish that balances simplicity with depth.  
', 
    '00:10:00' :: interval, '00:30:00' :: interval, 
    'Classic Spaghetti with Tomato Sauce', 
    1, NULL, 1, 2, '{
  "questions": [
    {
      "id": 1,
      "type": "single",
      "value": "How long should the tomato sauce be simmered?"
    },
    {
      "id": 2,
      "type": "multiple",
      "value": "Which ingredients are added to make the classic spaghetti with tomato sauce?"
    },
    {
      "id": 3,
      "type": "text",
      "value": "Why is it important to cook the pasta in salted water?"
    }
  ],
  "options": [
    {
      "id": 1,
      "questionId": 1,
      "value": "5 minutes",
      "correct": false
    },
    {
      "id": 2,
      "questionId": 1,
      "value": "10 minutes",
      "correct": true
    },
    {
      "id": 3,
      "questionId": 1,
      "value": "15 minutes",
      "correct": false
    },
    {
      "id": 4,
      "questionId": 1,
      "value": "20 minutes",
      "correct": false
    },
    {
      "id": 5,
      "questionId": 2,
      "value": "Pasta",
      "correct": true
    },
    {
      "id": 6,
      "questionId": 2,
      "value": "Tomato sauce",
      "correct": true
    },
    {
      "id": 7,
      "questionId": 2,
      "value": "Garlic",
      "correct": true
    },
    {
      "id": 8,
      "questionId": 2,
      "value": "Olive oil",
      "correct": true
    },
    {
      "id": 9,
      "questionId": 2,
      "value": "Cream",
      "correct": false
    },
    {
      "id": 10,
      "questionId": 2,
      "value": "Basil",
      "correct": true
    }
  ]
}
' :: json, 
    50
  );
